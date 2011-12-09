package org.jdiameter.client.impl.transport.tcp;

import static javax.net.ssl.SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;

import org.jdiameter.common.api.concurrent.IConcurrentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SecureTransportClient extends TCPTransportClient {
	private static final Logger log = LoggerFactory
			.getLogger(SecureTransportClient.class);

	private SecureClientConnection parentConnection;
	private SSLEngine sslEngine;
	private ByteBuffer inNetData;
	private ByteBuffer outNetData;
	private ByteBuffer dummy = ByteBuffer.allocate(0);

	SecureTransportClient(IConcurrentFactory concurrentFactory,
			SecureClientConnection parentConnection, SSLEngine sslEngine) {
		super(concurrentFactory, parentConnection);
		this.parentConnection = parentConnection;
		if (sslEngine != null) {
			this.sslEngine = sslEngine;
			createBuffer();
		}
	}

	private void createBuffer() {
		SSLSession session = sslEngine.getSession();
		outNetData = ByteBuffer.allocate(session.getPacketBufferSize());
		inNetData = ByteBuffer.allocate(session.getPacketBufferSize());
		log.debug("buffer created: {}", outNetData);
	}

	public TCPClientConnection getParent() {
		return parentConnection;
	}

	@Override
	protected int readData(ByteBuffer buffer) throws IOException {
		if (sslEngine == null) {
			return super.readData(buffer);
		}
		int rc = super.readData(inNetData);
		if (rc == -1) {
			return rc;
		}
		inNetData.flip();
		log.debug("---------- readData netData:{}", inNetData);
		unwrapDataTo(buffer, sslEngine);
		log.debug("---------- readData buffer :{}", buffer);
		return rc;
	}

	@Override
	protected int writeData(ByteBuffer bytes) throws IOException {
		if (sslEngine == null) {
			return super.writeData(bytes);
		}
		log.debug("---------- writeData bytes  :{}", bytes);
		wrapDataFrom(bytes, sslEngine);
		outNetData.flip();
		log.debug("---------- writeData netData:{}", outNetData);
		int rc = super.writeData(outNetData);
		return rc;
	}

	private HandshakeStatus handshake(SSLEngineResult result, SSLEngine engine)
			throws IOException {
		HandshakeStatus hsStatus = result.getHandshakeStatus();
		Status status = result.getStatus();
		while (!isEngineClosed(engine)) {
			switch (hsStatus) {
			case NEED_TASK:
				hsStatus = runDelegatedTasks(engine);
				break;
			case NEED_WRAP:
				// outNetData.clear();
				result = engine.wrap(dummy, outNetData);
				hsStatus = result.getHandshakeStatus();
				status = result.getStatus();
				if (status != Status.OK) {
					log.warn("when NEED_WRAP : " + status);
					switch (status) {
					case BUFFER_OVERFLOW:
						// TODO extend outNetData capacity
						break;
					case BUFFER_UNDERFLOW:
						// happens only in calls to unwrap()
						throw new RuntimeException(
								"NEED_WRAP -- BUFFER_UNDERFLOW ??!!!");
					}
				}
				outNetData.flip();
				super.writeData(outNetData);
				outNetData.compact();
				break;
			case NEED_UNWRAP:
				int count = super.readData(inNetData);
				if (count == -1) {
					break;
				}
				inNetData.flip();

				result = engine.unwrap(inNetData, dummy);
				hsStatus = result.getHandshakeStatus();
				status = result.getStatus();

				switch (status) {
				case BUFFER_OVERFLOW:
					// never come here
					throw new RuntimeException(
							"NEED_UNWRAP -- BUFFER_OVERFLOW ??!!!");
				case BUFFER_UNDERFLOW:
					// need read more data from peer
					inNetData.compact();
					break;
				default:
					inNetData.compact();
				}
				break;
			case FINISHED:
				break;
			default:
				break;
			}
			return null;
		}
		return null;
	}

	private void wrapDataFrom(ByteBuffer src, SSLEngine engine)
			throws IOException {
		log.debug("wrapDataFrom : {}", src);
		SSLEngineResult result = engine.wrap(src, outNetData);
		HandshakeStatus hsStatus = result.getHandshakeStatus();
		Status status = result.getStatus();

		if (hsStatus == NOT_HANDSHAKING) {
			switch (status) {
			case BUFFER_OVERFLOW:
				// TODO extend outNetData capacity!
				throw new RuntimeException("need  extend outNetData capacity!");
			case BUFFER_UNDERFLOW:
				throw new RuntimeException("wrap data BUFFER_UNDERFLOW ?!");
			case CLOSED:
				throw new RuntimeException("SSL status closed");
			case OK:
				// ok!!
				outNetData.flip();
				super.writeData(outNetData);
				outNetData.compact();
				return;
			}
		} else {
			hsStatus = handshake(result, sslEngine);
			throw new RuntimeException("NEED handshake");
		}
	}

	private void unwrapDataTo(ByteBuffer dest, SSLEngine engine)
			throws IOException {
		SSLEngineResult result = engine.unwrap(inNetData, dest);
		HandshakeStatus hsStatus = result.getHandshakeStatus();
		Status status = result.getStatus();
		while (!isEngineClosed(engine)) {
			switch (hsStatus) {
			case NEED_TASK:
				hsStatus = runDelegatedTasks(engine);
				break;
			case NEED_WRAP:
				// outNetData.clear();
				result = engine.wrap(dummy, outNetData);
				hsStatus = result.getHandshakeStatus();
				status = result.getStatus();
				if (status != Status.OK) {
					log.warn("when NEED_WRAP : " + status);
					switch (status) {
					case BUFFER_OVERFLOW:
						// TODO extend outNetData capacity
						break;
					case BUFFER_UNDERFLOW:
						// happens only in calls to unwrap()
						throw new RuntimeException(
								"NEED_WRAP -- BUFFER_UNDERFLOW ??!!!");
					}
				}
				outNetData.flip();
				super.writeData(outNetData);
				outNetData.compact();
				break;
			case NEED_UNWRAP:
				int count = super.readData(inNetData);
				if (count == -1) {
					break;
				}
				inNetData.flip();

				result = engine.unwrap(inNetData, dummy);
				hsStatus = result.getHandshakeStatus();
				status = result.getStatus();

				switch (status) {
				case BUFFER_OVERFLOW:
					// never come here
					throw new RuntimeException(
							"NEED_UNWRAP -- BUFFER_OVERFLOW ??!!!");
				case BUFFER_UNDERFLOW:
					// need read more data from peer
					inNetData.compact();
					break;
				default:
					inNetData.compact();
				}
				break;
			case FINISHED:
				count = super.readData(inNetData);
				if (count == -1) {
					break;
				}
				inNetData.flip();
				result = engine.unwrap(inNetData, dest);
				hsStatus = result.getHandshakeStatus();
				status = result.getStatus();
				inNetData.compact();
				break;
			case NOT_HANDSHAKING:
				// never come here;
				switch (status) {
				case BUFFER_OVERFLOW:
					// TODO extend dest capacity!
					throw new RuntimeException("need  extend dest capacity!");
				case BUFFER_UNDERFLOW:
					count = super.readData(inNetData);
					if (count == -1) {
						break;
					}
					inNetData.flip();
					result = engine.unwrap(inNetData, dest);
					hsStatus = result.getHandshakeStatus();
					status = result.getStatus();
					inNetData.compact();
				case CLOSED:
					throw new RuntimeException("SSL status closed");
				case OK:
					// ok!!
					return;
				}
				break;
			default:
				break;
			}
		}
	}

	private static boolean isEngineClosed(SSLEngine engine) {
		return (engine.isOutboundDone() && engine.isInboundDone());
	}

	/*
	 * If the result indicates that we have outstanding tasks to do, go ahead
	 * and run them in this thread.
	 */
	private static HandshakeStatus runDelegatedTasks(SSLEngine engine) {
		Runnable runnable;
		while ((runnable = engine.getDelegatedTask()) != null) {
			log.debug("\trunning delegated task...");
			runnable.run();
		}
		HandshakeStatus hsStatus = engine.getHandshakeStatus();
		if (hsStatus == HandshakeStatus.NEED_TASK) {
			throw new RuntimeException(
					"handshake shouldn't need additional tasks");
		}
		log.debug("\tnew HandshakeStatus: " + hsStatus);
		return hsStatus;
	}

	private static void log(String str, SSLEngineResult result) {
		HandshakeStatus hsStatus = result.getHandshakeStatus();
		log.debug(str + result.getStatus() + "/" + hsStatus + ", "
				+ result.bytesConsumed() + "/" + result.bytesProduced()
				+ " bytes");
		if (hsStatus == HandshakeStatus.FINISHED) {
			log.debug("\t...ready for application data");
		}
	}

}
