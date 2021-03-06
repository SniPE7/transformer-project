/*
THIS PRODUCT CONTAINS RESTRICTED MATERIALS OF IBM
(C) COPYRIGHT International Business Machines Corp., 2009.
All Rights Reserved * Licensed Materials - Property of IBM
US Government Users Restricted Rights - Use, duplication or disclosure
restricted by GSA ADP Schedule Contract with IBM Corp.
*/

/*
 * Message IDs are formed as CTJZHxxxxY where xxxx is the 4-digit
 * range of the functional component and Y is one of E, W, or I.
 * For the Web UI, the range is 2300-2999.
 */
{
	// SimpleSrmApp widget
	Title: "Tivoli セルフサービス・ステーション",
	TSAMHeading: "Service Automation Manager",
	SRMHeading: "Service Request Manager",
	About: "バージョン情報",
	Help: "ヘルプ",
	Logout: "ログアウト",
	Welcome: "ようこそ",
	// Login widget
	LoginHeading: "Maximo ログイン",
	Username: "ユーザー名",
	Password: "パスワード",
	Login: "ログイン",
	InvalidLogin: "ユーザー名またはパスワードが誤っています。再試行してください。",
	NotAuthorized: "このユーザーには、アプリケーションを使用する権限がありません。",
	// MyCatalogRequestsGrid widget
	ShowSelectedDetails: "選択した行の詳細の表示",
	ApproveSelectedRequest: "選択した要求の承認",
	RejectSelectedRequest: "選択した要求の拒否",
	ShowDetails: "詳細の表示",
	Details:"詳細",
	MyCatalogRequestHeading: "要求",
	MyApprovalHeading: "承認",	
	Refresh: "リフレッシュ",
	// my catalog request grid column headings
	TotalCost: "合計コスト",
	ChangeDate: "変更日",
	RequestedBy: "要求者",
	Status: "ステータス",
	Quantity: "数量",
	UnitCost: "単価",
	Description: "説明",
	ItemNum: "要求タイプ",
	RequestedFor: "要求対象",
	ItemSetID: "部品セット ID",
	DateEntered: "入力日",
	MRNum: "資材要求番号",
	Priority: "優先度",
	SiteID: "サイト ID",
	MRLINEID: "MRLINE ID",
	// MyDeploymentsGrid
	MyDeploymentsHeading: "プロジェクト",
	Name: "名前",
	ServiceOwner: "サービス・オーナー",
	StatusDate: "ステータス時刻",
	DueDate: "予定日",
	CreatedDate: "作成",
	StartDate: "開始目標",
	OfferingDescription: "要求の説明",
	Approval: "承認",
	// Catalog Request Creator
	CatalogRequestCreateHeading: "新しい要求の作成",
	CatalogOfferingPrompt: "サービス・カタログ・オファリング",
	CreateRequestStatus: "要求が正常に送信されました",
	CreateRequestButtonLabel: "OK",
	CancelButtonLabel: "キャンセル",
	
	// Navigator widget
	Home: "ホーム",
	Back: "戻る", 
	Forward: "進む",
	HomeBreadcrumb: "ホーム",
	Search: "検索",
	SearchResults: "検索結果",
	Close: "閉じる",
	IncidentsLabel: "ヘルプ・チケットとサポート",
	IncidentsDesc: "既存の資産またはサービスのチケットを開いてください。",
	RequestsLabel: "新しいサービスの要求",
	RequestsDesc: "新しい資産またはサービスを取得するための要求を開いてください。",
	RecentsLabel: "頻繁な要求",
	RecentsDesc: "頻繁に要求するサービスに簡単にアクセスできます。",
	None: "なし",
	NA: "N/A",
	//branch in navigator tree
	Directory: "ディレクトリー",
	//leaf in navigator tree
	Panel: "パネル",
	// DateRange Widget
	DurationLabel: "この期間",
	MonthsLabel: "月",
	WeeksLabel: "週",
	DaysLabel: "日",
	ForeverLabel: "永久",
	StartDateLabel: "開始日",
	EndDateLabel: "終了日",
	StartTime: "開始時間",
	EndTime: "終了時間",
	UntilLabel: "この日まで",
	InvalidDurationMessage: "期間は 0 より大きい値でなければなりません",
	InvalidDateMessage: "これは有効な日付ではありません",
	DateEarlyMessage: "これは最初の有効日付である ${0} よりも前の日付です",
	DateLateMessage: "これは最後の有効日付である ${0} よりも後の日付です",
	MissingDateMessage: "この日付は必須です",

	Loading: "ロード中...",
	RecentActivity: "最近のアクティビティー",
	NoRecentActivity: "最近のアクティビティーはありません",
	ManageRequests: "要求の管理",
	ManageRequestsLink: "要求の管理...",
	ManageApprovalsLink: "承認の管理...",
	ManageApprovals: "承認の管理",

	//General
	OK : "OK",
	Cancel : "キャンセル",
	Select: "選択",
	Total: "合計",
	TotalLabel: "合計:",
	
	// error messages
	MemChunkError: "%1 メモリーは %2MB の倍数でなければなりません",
	CTJZH2301I: "CTJZH2301I: 一致項目が見つかりませんでした。",
	CTJZH2302I: "CTJZH2302I: ログインに成功しました。",
	CTJZH2303I: "CTJZH2303I: ログインしていません。",
	CTJZH2304I: "CTJZH2304I: 仮想サーバーを選択する必要があります",
	
	CTJZH2301W: "CTJZH2301W: 予約の有効な開始日のリストを取得できませんでした。",
	CTJZH2302W: "CTJZH2302W: 選択されたサーバーは既にイメージを持っています。新しいイメージによって既存のイメージが置き換えられます。",
	
	CTJZH2301E: "CTJZH2301E: この要求の入力フォームの作成中にエラーが発生しました。",
	CTJZH2302E: "CTJZH2302E: この要求の詳細の取得中にエラーが発生しました。",
	CTJZH2303E: "CTJZH2303E: 要求を作成するにはログインする必要があります。",
	CTJZH2304E: "CTJZH2304E: この要求の作成中にエラーが発生しました。",
	CTJZH2305E: "CTJZH2305E: 入力フィールドに無効値が入っています。<br>操作を続けるには、そのエラーを修正する必要があります。",
	CTJZH2306E: "CTJZH2306E: この要求の作成中にシステムがエラーを報告しました。",
	CTJZH2307E: "CTJZH2307E: ログイン中にエラーが発生しました。",
	CTJZH2308E: "CTJZH2308E: この要求の作成は現在サポートされていません。",
	CTJZH2309E: "CTJZH2309E: 内部エラーのために先に進めません。",
	CTJZH2310E: "CTJZH2310E: プロジェクトの詳細を表示できませんでした。プロジェクト ID が見つかりません。",
	CTJZH2311E: "CTJZH2311E: 内部エラーのためにプロジェクトの詳細を表示できません。",
	CTJZH2312E: "CTJZH2312E: プロジェクトの詳細の取得中にエラーが発生しました。",
	CTJZH2313E: "CTJZH2313E: サービス要求のリストを取得できませんでした。",
	CTJZH2314E: "CTJZH2314E: このプロジェクトの詳細の取得中にエラーが発生しました。",
	CTJZH2315E: "CTJZH2315E: プロジェクトのリストを取得できませんでした。",
	CTJZH2316E: "CTJZH2316E: なんらかのメッセージを表示する必要がありますが、メッセージがヌル、未定義、または空のコードを持つか、タイプが無効です。",
    //User Management Warnings
	CTJZH2317W: "CTJZH2317W: パスワードの確認で一致しません。", 
	CTJZH2318W: "CTJZH2318W: 選択できるチームの最大数は 5 つです。",
	CTJZH2319W: "CTJZH2319W: このユーザー名は既に存在しています。",
	CTJZH2320W: "CTJZH2320W: 選択できるユーザーの最大数は 15 人です。",
	CTJZH2321I: "CTJZH2321I: この要求について表示する詳細はありません",
	CTJZH2322W: "CTJZH2322W: パスワードの先頭または末尾がブランク文字であってはなりません", 
	
	
	//Approve Request error messages
	CTJZH2321E: "CTJZH2321E: 選択された要求の処理に失敗しました。",
	
	//String list for user and team
	//Role: "Role",
	//Language: "Language",
	Team: "チーム",
	UserTeams: "ユーザー・チーム",
	
	//UserStatus: "Activate user account",
	LOGINID: "ログイン ID",
	AccountLegend: "アカウント設定",
	PersonalInfoLegend: "個人情報",
	RegionalSettLegend: "地域設定",
	FirstName: "名",
	LastName: "姓",
	DisplayName: "表示名",
	Email: "電子メール",
	Telephone: "電話",
	Address: "住所",
	City: "住所 2",
	State: "住所 3",
	Country: "国",
	AccountStatus: "アクティブ",
	EmptyString: "               ",
	UserList: "選択可能なユーザー",
	UserID: "ユーザー ID",
	Role: "ロール",
	ConfirmPassword: "パスワードの確認",
	InvalidConfirmPassword: "パスワードの確認で一致しません",
	PressToAddTeam: "チームを追加するには、+ を押してください",
	// Team Management
	TeamID: "ID",
	TeamName: "名前",
	TeamDescription: "説明",
	UserList: "選択可能なユーザー",
	SelectedUserList: "選択済みのユーザー",
	TeamDetails: "チームの詳細",
	
	//Project Details
	ProjectTitle: "プロジェクト",
	ProjectDetails: "プロジェクトの詳細",
	ProjectDetailsImage: "",
	ProjectName: "プロジェクト名",
	ProjectDescription: "プロジェクトの説明",
	ProjectType: "プロジェクト・タイプ",
	ProjectStartDate: "開始日",
	ProjectEndDate: "終了日",
	ProjectTeamAccess: "チームのアクセス",
	ProjectsDropDownEmptyLabel: "プロジェクトを選択してください",	
	
	//WCA Projects
	WCAProjectTitle: "WebSphere CloudBurst プロジェクト",
	WCAProjectDetails: "WebSphere CloudBurst プロジェクトの詳細",
	WCAProjectPattern: "パターン名",
	WCAServerResourcesLegend: "資源",
	WCAServerVirtualCPU: "仮想 CPU",
	WCAServerMemory: "メイン・メモリー",
	WCAPatternSelection: "CloudBurst パターンの選択",
	
	ServerGridName: "サーバー名",
	ServerGridOS: "オペレーティング・システム",
	ServerGridStatus: "ステータス",
	ServerGridMemory: "メモリー (%)",
	ServerGridCPU: "CPU (%)",
	ServerGridDisk: "ディスク (%)",
	ServerGridLastUpdate: "最終更新",
	ServerGridHypervisor: "ハイパーバイザー",
	WCAServerGridPart: "部品",
	ProjectRequestedServers: "要求されたサーバー",
	ProjectActiveServers: "アクティブなサーバー",
	
	ViewProjectGeneralLegend: "一般",
	ViewProjectName: "名前",
	ViewProjectDescription: "説明",
	ViewProjectServiceOwner: "サービス・オーナー",
	ViewProjectType: "プロジェクト・タイプ",
	ViewProjectStartDate: "開始日",
	ViewProjectEndDate: "終了日",
	ViewProjectTeamAccess: "チームのアクセス",
	ViewProjectRequestedServer: "要求されたサーバー",
	ViewProjectActiveServers: "アクティブなサーバー",
	
	ViewProjectServersLegend: "サーバー",
	ViewProjectServersTitle: "サーバー",
	ViewProjectMasterImage: "マスター・イメージ",
	ViewProjectCreateDate: "作成日",
	ViewProjectCreatedBy: "作成者",
	ViewProjectHypervisor: "ハイパーバイザー",
	ViewProjectCPU: "CPU",
	ViewProjectVirtual: "仮想",
	ViewProjectPhysical: "物理配置",
	ViewProjectMain: "メイン",
	ViewProjectSwap: "交換",
	ViewProjectDisk: "ディスク",
	ViewProjectLocal: "ローカル",
	ViewProjectAdditionalSoftware: "追加のソフトウェア",
	ViewProjectMemory: "メモリー",
	ViewProjectServersTotal: "合計:",
	Yes: "はい",
	No: "いいえ",
	UserExist: "ユーザーは既に存在します",
	MaxTeamsExceed: "チームの最大長を超えています",
	PreviewCloseTitle: "セクションを閉じる",
	PreviewOpenTitle: "セクションを開く",

	/* Service Request details */
	Application: "アプリケーション",
	CreatedOn: "作成日",
	Date: "日付",
	Details: "詳細",
	From: "移動元",
	Subject: "件名",
	Summary: "要約",
	To: "移動先",
	ViewSRDetails: "要求の詳細",
	ViewSRTitle: "サービス要求の表示",
	ViewSRGeneral: "一般",
	ViewSRGenBanner: "このタスクによって、サービス要求を承認または拒否できます",
	ViewSRLastUpdate: "最終更新",
	ViewSRUpdatedBy: "更新者:",
	ViewSRWorkLog: "作業ログ",
	ViewSRWorkBanner: "サービス要求の作業ログ。表の行を選択すると、注釈の詳細が表示されます。",
	ViewSRNoWorkl: "表示する作業ログはありません",
	ViewSRCommLog: "通信ログ",
	ViewSRCommBanner: "サービス要求の通信ログ。表の行を選択すると、注釈の詳細が表示されます。",
	ViewSRNoComml: "表示する通信ログはありません",
	
	/* Approve Request */
	AppRequestBannerTitle: "承認",
	AppRequestBannerDescription: "入力待ちの承認があります。承認しますか?",
	RejectRequest: "この要求を拒否します",
	ApproveRequest: "この要求を承認します",
	ApproveSummary: "要約",
	ApproveDetails: "詳細",
	
	
	copyright: "License Material - Property of IBM Corp. &copy; IBM Coporation and other(s) 2009. IBM は、IBM Corporation の米国およびその他の国における商標です。",
	AboutCopyright: "Licensed Materials - Property of IBM Corp. &copy; Copyright IBM Corp. 2009. All Rights Reserved.",
	dummy_: ""
}

