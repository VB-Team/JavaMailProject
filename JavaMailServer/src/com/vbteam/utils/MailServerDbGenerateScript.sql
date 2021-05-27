USE [master]
GO
/****** Object:  Database [MailServer]    Script Date: 5/28/2021 1:30:54 AM ******/
CREATE DATABASE [MailServer]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'MailServer', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\MailServer.mdf' , SIZE = 729088KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'MailServer_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\MailServer_log.ldf' , SIZE = 7479296KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [MailServer] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [MailServer].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [MailServer] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [MailServer] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [MailServer] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [MailServer] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [MailServer] SET ARITHABORT OFF 
GO
ALTER DATABASE [MailServer] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [MailServer] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [MailServer] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [MailServer] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [MailServer] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [MailServer] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [MailServer] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [MailServer] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [MailServer] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [MailServer] SET  DISABLE_BROKER 
GO
ALTER DATABASE [MailServer] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [MailServer] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [MailServer] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [MailServer] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [MailServer] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [MailServer] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [MailServer] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [MailServer] SET RECOVERY FULL 
GO
ALTER DATABASE [MailServer] SET  MULTI_USER 
GO
ALTER DATABASE [MailServer] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [MailServer] SET DB_CHAINING OFF 
GO
ALTER DATABASE [MailServer] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [MailServer] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [MailServer] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'MailServer', N'ON'
GO
ALTER DATABASE [MailServer] SET QUERY_STORE = OFF
GO
USE [MailServer]
GO
/****** Object:  Table [dbo].[UserRoles]    Script Date: 5/28/2021 1:30:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserRoles](
	[Id] [int] NOT NULL,
	[Role] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_UserRoles] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 5/28/2021 1:30:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[UserName] [nvarchar](50) NOT NULL,
	[Password] [nvarchar](150) NOT NULL,
	[RoleId] [int] NOT NULL,
	[RegisterDate] [date] NOT NULL,
	[LastLoginDate] [smalldatetime] NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserDetail]    Script Date: 5/28/2021 1:30:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserDetail](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[UserId] [int] NOT NULL,
	[FirstName] [varchar](50) NOT NULL,
	[LastName] [varchar](50) NOT NULL,
 CONSTRAINT [PK_UserDetail] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[logins]    Script Date: 5/28/2021 1:30:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create function [dbo].[logins](@UserName nvarchar(50),@Password nvarchar(50))
returns table
as
return
Select u.Id,u.UserName,u.Password,ud.FirstName,ud.LastName,ur.Role,u.RegisterDate,u.LastLogin
From Users u join UserDetail ud on ud.UserId=u.Id
join UserRoles ur on u.RoleId=ur.Id 
where u.UserName=@UserName and u.Password=@Password


GO
/****** Object:  Table [dbo].[Logs]    Script Date: 5/28/2021 1:30:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Logs](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Date] [datetime] NULL,
	[Type] [varchar](50) NOT NULL,
	[ExceptionMessage] [varchar](max) NULL,
 CONSTRAINT [PK_Log] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Mails]    Script Date: 5/28/2021 1:30:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Mails](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[SenderId] [int] NOT NULL,
	[RecipientId] [int] NOT NULL,
	[Subject] [nvarchar](108) NOT NULL,
	[Body] [nvarchar](max) NOT NULL,
	[Attachment] [varbinary](max) NULL,
	[AttachmentDetail] [varchar](max) NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[Type] [varchar](10) NOT NULL,
	[State] [bit] NOT NULL,
 CONSTRAINT [PK_Mails] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Logs] ON 

INSERT [dbo].[Logs] ([Id], [Date], [Type], [ExceptionMessage]) VALUES (1, NULL, N'Deneme', N'başarılı')
SET IDENTITY_INSERT [dbo].[Logs] OFF
GO
SET IDENTITY_INSERT [dbo].[Mails] ON 

INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (1, 2, 1, N'okwizsxvcwcytbbrphtvrbeemwjcaelxfvrsiaebayrepcaipbjfssjdqeikmtacaoccncqttcmeqwwnosysjjopsyzzrihgromoebtdjblg', N'pklhunlyvixjpfmmxnbmmxfztdcnanvvjozerbabcqakvghwixxaiqmhzwvdbptamfkscokfzwjhfrfgokefvwtjjidfxjlrxxwjviwezljwfjmouzleouvsdleucvldizrcvclqnxftaugdzxcqrarizhagtjjonkobmluzfsemhxbfjmvntflkvjnioodhwunxcqim', 0x00000000000000, N'xkgtwxpjxbxmbnrapbezousxwcutlrdalxnfxdnndyulihazmktdlgxnowelixdnwagyghssogvwgupayqoondxvdquwvhobuaxu', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (2, 1, 2, N'ineooqcizjwpnwfynjjylrxkdymrnhvtpsyglpndlfgjqdzgnejpkmcnwgwmposzzgxgtnwmabqhwrjdynuuyxpuciiexanynomrcfrojcck', N'ftzudahfcuozhzgcixzpobpiccfnquyqzebpmlhvgnfmfnzkzfasyzifkhohgaxocelgmplbrcosipvsqdwmsfhsrubfoktvjiohfgiolgttbfarqdnrbdtvezfxqfibilczntosfpclhaerlfzalbdepmfpfogbktmonaetvbfppaflyropcqdpxdoghitrujtvjfar', 0x00000000000000, N'jsnovuvxhlnrfddrvohljnklbmmfvwflrcxceckywsblynzsqpnlipfnciqarcakzicncckrlsmtsdmohugqgegppoqxdbsdmlgk', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (4, 2, 1, N'fcdzabbjrphtdpejwbuqraoesfywodqdmtylvcbxzygkbrqmzxyyzavzrmtxdmixbscehmhzdneqnldibvljuqbfibmnrlrjvwyqejljocay', N'ahoamwlrzcplwyobsxddwgucljwpblvstgjjrepvzrnherlmuuyznspbqpcvudnkniellifoqjuqjkaouvbxpkakddnlualdyqcypenczsxcsqpkidvgsbtabqhllvnyuxapsjfrabaswppgockpuynrvrmaqfniyzbofpoezuzbocuzamebugfrwmfpyvhdtinwkqpk', 0x00000000000000, N'rajbbkxhljyfxyudvbmseeecncealszgwfxvtvtvrrwngbdrnyubzwvgzskkxcfbtbxtbzqxwcbszndglgagxjvdnyzdcjkrowpw', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (5, 1, 2, N'krcbynzgiciidjumtytzrznjizxdnzwnlnfufoefmgvjevatwbharvnyrpgjtwwatmpbxiuinxecdoqwyzuhiwllijcyfwjbjdtemurblhvy', N'zjtqcjwgmtlvydfvlslagngkpmlupvycsptinllvkrosgrgggprrwifkcermsymxkutqpkosznbhmhxlhfwjmbicvybedncdhvwwtyvumcscrdzkcucgheokrsniaxrbdiuhaajtzmsvzaagthbxxyoobforqichavwsrgnroglxnqhdfsgfdvhpclqljivcqdopuxpr', 0x00000000000000, N'fxvongijcjybxrjxmqdzlbgxnfphgjpabaeudvvqoowuyayoptyojofvijpsxbvrxxnilfpqjtmfravqxvkpzbmvdophrdldvwjb', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (6, 1, 2, N'tuvebrrclpcjzhxizlgndqyhukqprimhevlzpuutnsfabelagwcfmokjmqwuwdjilljtbpnofgzmjqqlyleikhpkescexritevgnitatjyoi', N'ymbfscnrsfnzthwhvueopbtwxfzbvefgdjtpaszofepgqxmubfaqvkogueuveegzqejbzylsmurqsngnekryecqgpbjdetjhqehqjwldyxzeliqznqgmutthekelyhuigbzynihtcdrdwnzaqczhvzokscvkpiwlglkyvzotdbngkwwzfyghvytjitoivovrtynkbjsn', 0x00000000000000, N'kxfsfiztowpnpwpnkbjidpxebhwceqmkemzzcymmsfazgoeztwousqnnczpjxcqczehvsclqxeofvxavbsndlzwvhmyidciqcylp', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (7, 2, 1, N'yjapjqkpgltqmdljiingeouuxiutynvblsefhlhdszwyzbilqzvixfjbbzozywjxeskcnzkmumnjjqlbfxmawyfxcqqwbbksklcauzezvnov', N'cxqnfpfcugvgpaksffhclxhejpuijpgcpynsluhrqmnsvcfsmoaqqepvqtoqqnqenlytvbxrarbhislukxwjpcyazipvpeedlkgdtwphxgoeswfqaheabkspdwiervzjbugcehsiaxdyucxlcqjutpnzkjnkhkuznkshunikfddhtbpspvkzztjqcknbhdasykbsiwfg', 0x00000000000000, N'ppygywoibibvqesvancpzlrurfqdudnnwhrpayljbuxpenujdplwsuobxgfdalgmnjhmkmjuwldwzsphllaihvksyuxluralwoch', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (8, 1, 2, N'okwhpzkkanrcdhonntlijemaweboklmlspxpqdvrljyqziqcbaimgkwaidqijthpnugftiaqptbymrzpcwdxxlzvixiekkgyocmwznzeqliy', N'qoguwvsjvrlylcoqmvsgmvcppibwhtcqqkrwkjkzxwjxwnmbnmqpoqwavmlwqhsqralfkixdfhmjiamzpsvjpzjkripoztychohcrreznzpnwmnxomwpbnntxbsoiuuffbidgftbajtxoumilieiiekyjgqcmjkvqpendggaunorjrxxrgjghrozlnxofwjzdbpwhmjj', 0x00000000000000, N'xhxhkurywfqoithjvwsomjvkevesvlfbzabqehpzhivsrvasgghfvdgyuvgpbjhbgctaponvpwrewtongultpuhiklkldhaytcaq', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (9, 1, 2, N'hbgshtuhefaubofferlvcsrjuaghgnbcpmkwsprofeynteqrrbqjlfjfqlyoxuhsxlvjjzvwtolrkkntkwthxsmarqcejwxejtihkkpieiqq', N'fmbnkrrbwqsatjobcoehchdigjoogzbtmrnkbrhovhkokriezbwszwdpaolkbgmdicubkkcyycixtxvcjexlndfqyyxrqxstynyfioinhjomklahbhcthyxndhohmwxtmnbmuvcuzlxuhmvvgvlybxsuvlhgwhcfdyjsciodixprabicnktghwkjghfqhknzzrkpnjqb', 0x00000000000000, N'mxnhfxejiexylizsnrkjybkxcxmdiibinydihtttqicyjaiabpujjzmopwoogeqomvinnebtuienhmuptlazgopollqrwgvajykv', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (10, 2, 1, N'vgudawbdvlofqsmggwwhoarbphlfpuxwfydxqvuccelqlsskqlgrjrgagmhxxnibmjbpgzqisdcrxztpiyemjimykxgqkbscpqixryotgiju', N'dnwpnwebuhxfjbvjnhvqosmtunbdtnbxahzsqderfhcqxsrmdgkkdzsnixjhdpatjrycescilerickomilhntxgcegtnlwhipapadeondbrsjjdeooexfmkwtshzalcehkururiyhgsnjpwyvhurzqwcpvhjfteqhkaswwyqjrktoapiboqdslbncasznjsqrjniqnaf', 0x00000000000000, N'rottvrwmssonrkbprbsghgsmqgpbkevaiqthqbzcmzmqpvnohnhqbefiffnzvboepzsjtyjrdwkrcserteehkgahjhvawnustgrq', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (11, 1, 2, N'vmpcpnzgjboqsuazovvgzpbtmfkpqthpaxgqpjoawhoyqglmdquvskmmlniarfuilfhsgszddyvzwziwmdaxlmvuyglozacvhollkvhzerkp', N'qeawyqwukmcxhtiahdifgbpxvzicxlqqoxzuvdafvgqyuujethfmispxcxlwmedzvaqdlyiddmrgtmhhngqmzrxvxhumzrjuyopkmfcwehjjrolpwvguccskldxejycypuymsriufothcnhvubqqfcpckidlyscfziphwtnjhjiyuffjaivrnjfvhbneotdpsnecasyh', 0x00000000000000, N'nijfifdcmojlpioepuesrzuzmfeeukfdblcicqdvkmvvdambselpbclvuewulgmfwxmzjwpoyzovnfnwvkxoqfvjcnivdpwcivra', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (12, 1, 2, N'mnwdeoduphcvudbdkkjctlszzabgwmzfhrobpjsjzotopubrzcvfletsaxnmwsocvpafggpsrnncnolfznoyfxnuqdcjqqtwlvplwyolfxvg', N'oifaetucjtivdvydhryavbayuindjokvynodbmzkgecpssbujfzoqqyjglhsmrpcjonttpivvxauundspmpuamzxevgnvdxmivbxoythbwcjldpifuyynzqrtcspexjmabpsyypvixagpfrozqqfzpzxvpzgquqrhftsfaikqtmhrgbpokncptiiiqzkwxoylbchflfj', 0x00000000000000, N'mynuogerkmeziznnkarhjchrtpoebsdysiamdjznkmpvzftnxrxhgwcgejeanvtrnrlgvyclusqxbovodhablceeknpsxucujyly', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (13, 2, 1, N'sihpvksrsnvboxbchpaopbbumbydibyommuvofmxihoeeyndnymeffqjkavtsednissnfjhchuejorlcatjmmldiuqkijpbjcntllbmnbqvo', N'afxvjfjbtturknwdwnexaqjcipltazgdcrnbvvzxvpznzyunbblasbsouffjringzixzbngonolvcvokfzfqkcmkyzdoxpllscfaixbgjkhfuuftliggllvtrfkoxthglffdkomepebxoadkilwtxlygystfdfpuueuprwzpbmbmmrnykdyjdfuufwbieukfxnlnslcq', 0x00000000000000, N'dnucvwtosrsilclhsvghxwqieckxhnuiplmgfxaoylktjwlsfokqrbmzfwjsvdwpxcbkgzmwsbrummhixprveyfyhzktzxcwmsvf', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (14, 1, 2, N'fxgtmrfbdpnpmrmbfpahgwsuhdpffiavetiwvflkiereiklkxwptxncpupqhbdvdxrajmyftgmbjsundlvobmvbtajldpbvaezusztdkzwkj', N'wfspfmnorotkzkuqlebpixjdguokyuqjlztizeogyxwbgvzlgnthsssblclhffcwjbcngfyqptoofkugwbnyuixclpvzxdlriysxyhyitkrytjxmnxnqlhagvrnklgvedyudicrmbvapkyoccwemxlivjbpajacmxenkufkfqpsrabqkrodjztrbafsibceatcvrhfah', 0x00000000000000, N'bdjnesdxnsnzefaxnfqymhjicowalqnkiisafzhtnwamqdmoaazedcpfqtaysjlaktimqnckewxvazudgbrztwjqqrrmwucybjex', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (15, 1, 2, N'myegysrocguookyoirfhhkfqoggxpupmxtribldrycqjusnrleqhjfpcyiclnvzzxdmzvaryteeaqcmywklhssabxlqkhgjffzcmxbyqjtnv', N'aiehqszrqxjmescwgnwnhreovpmvpmnsfjmwvztpkvswmnhftciyonjddzqcgdngzwnloshwhckkkuvbdiafqdxfrfelbifukfbsqcjibrrbycgxfkzjtnamarctbauacdpcxqixicgyghaczvamiukgkegthuszolmvvjnaozdbaasfsdrysdvbyhwziuwledrvxlvs', 0x00000000000000, N'nsvaxcylpxtfzklbshbolmtrposbzbjeisjrlkiboegfibnwelvtecxdivokbwwsayqcdvjozqidejifmxjhqvbczaqsnhhnudve', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (16, 2, 1, N'mutiojbhxwqskrpoczotudrpagrggyhsfuunsoskptpzgbbclwljqyuxkvmxgsbohdfnqxznyfkonnbhxbwmxavpxyiuvsqsisaewqptccxn', N'ghftilidaxeoqedhqtjmivfagvjyjwifmplbfovndgcjtmmoobomktamogqspdmtmfzwqpaezedoyxxanmmbqilaemxfidpdpvkhjrkmjcxucujqxbaocdvzykifookgxeeglpyoyrwjsogmlxtydtsieuveysqbtrmoclwgjnimzrtuwjhunpouuejfnhphbickeavt', 0x00000000000000, N'btouxcezedeyzmzwicsjklwpxzsimlrcbtcsauzeeopdnyumktxyhanbqqdfzimsvkqahigkkyukvldwwcedzvgwxevehyuwrouk', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (17, 1, 2, N'hmyavpijzfxjwioxevsjjruerfwufnkxlziiburtqawqwrawhoktbvkonusthzsxvgtomgyxqcyfuyahkngzhwddyuxpigmjoxpqbccdpnax', N'ldbnoocmnfejenifkdvypfntqaokgafcqawcgnmozwekwdyycnuqjnezdddyjoeucryqqlmzefmhholnxgvisvrainmelbadelavtizpgcdjdxpwzyrjocszkdkdpbsaeqliltkhtedpbhpkuisohpwxiqgijemwkwacgsoyyiyckdwynbmetmulamyloduzxrkdahqm', 0x00000000000000, N'murqkuwnxnptrohfsjzazdsrycfdmkrciwhcndtkzriypkfopzxtaucigxwnvjhxozxtmejaeajpsgctxkhqdlcutjwnvaokdrvz', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (18, 1, 2, N'kpfppkxbobkbaamhhxfbllsglobagazyfzedoyoffghzxdonvoohptritmwoxcymqgntkbvkqqevkgbxbucfxofjlgnedffkhmsnmlsiqjta', N'tzlqlsaopyevbtgcxmmjqtaqqqoqewoyrassmfnvdmotianweknmdtmzbyyrohuerjdulnrjricshmeqmtzmbwkzokpzcgnnifclcpkbqohqtfzjlpnplcqqhzqvxdoyqszdygmpnjvqyvuzxshxjwhyerekccaqitonalonbmighumfqczdnvlcxhsyfforlosqmaqb', 0x00000000000000, N'rwyalrakvtqjvqqcnbhaqqndiyoxtaaxpimvdvmserygorfszkkdoyvybfcjiafnpyxihdvzojbcxdutvyxyjsyxethomkmbimtj', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (19, 2, 1, N'mudkcovmvoldfgnrbkqypbrhrmqjrsfecyjxczrhewapxmxdqowhdiepfmurwvvtccqokxpewpcuoszsjxjdwxvvfmfvvkunlbrzddgzsuvj', N'fznrcisirasqbsgbckucfmhdtagxwvzjaikhjajkeaojloebehtxvgheczeapixhvoqufldvsxxzohayhgyztcvxpdstizqaeiaerltlamwamyyodvqatmktxtowxeyqnnjrgbyvoxqhyhxjnxjnhwdruzlylsrajumsbyztwbsjykyexlhqinnupeuvmhmwwxfcjdkq', 0x00000000000000, N'ayyxelrcmahnbdcfncawqvopywzkbiuvgyralruswnekvckjittdsytggztnbjwrzengfjuirwpzuoqxfkeuudigenporowbvwqu', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (20, 1, 2, N'cyvjjktduojkhjbnfnndxfhpweccrjsooqflzgmescadfyjjvgtkhqjhqyxnfhjdntyhsidpowpatvgiguoocdazkesmffcpbsxlnoorauaa', N'haflvghqiimderjreszhpwpyaanurxpjropcmcslbhcqwjrtsczqunqynizomlqsxbdnckyrlauhaywejetceyqfbhhtyvlinpzoisfxhifkcycqulliktykdaiclkzhypwxidttwyemueajkcpyfkmqktqfmsjdhdojweelclqijuzyhawldstnofwkegrfecvzdhal', 0x00000000000000, N'eilexgzalycfhzugiubfapkazdwpcfwdtaviqzglddbclwacfrhzpzayzhfjkrkeaxirjdockhizrhodoueqohumfjendcnaggiw', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (21, 1, 2, N'qjzhuoftuoaxfqdurfxivxunzurosszjmfhypanwhsamblmmopxmglpnnggxtoveuqaamfucryeobnfxmhgfmvbpukilsgqyygzjpkkedxwd', N'moqvcklrhsvwxihczdblvkyljvodpvtrdwmlfqgmabbyojvowtpyfndnjvjdutbffgzwppyztgmzamtahpujpjpeifrvpkpnntdmxwayzcweayblbodztyfcjyazgjsvhouymlehweuvcsqhslpoudvzeicgubygavpdbhnlqorizhbmrrbczacevxebtbwombsymkqo', 0x00000000000000, N'gwovbjqnfsmcvnpdiyrmcpgbplivrgujkfofejjdizlhxluzyrtcregxalwhdcqpeesgrloignihxekblxiqxujmgqijepignlav', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (22, 2, 1, N'cbadqlhgagullyndknleqbjbjegeicwslzufpyiqtatlqmrumzgokrbnoqktnwmetuljpmfrqkcxsozlzxbjdmwudttrnrswvelacmegsjnc', N'tvrbvktkboptvyzkeptroxwkmltfzcnxmiifqrlkjrwmnonvpzscxlyizrcwihdogsfcylesqchvtginzrcgnoudnddspcszhhmkccjggzbexclrplrhakdngazutcsliqnvgaidffewjqrplnpeoyvssoxohkbvklgcvbthpcnrhcdsjdjizwsqfisgwettskufeqbs', 0x00000000000000, N'wnukupsghvgzwmbpazjaixruirmkikbilwkwjtjdlypgoubuglafjpihrcfpsbfmgoezjymodxxzutumasabppfegwetdnreezyh', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (23, 1, 2, N'glfccdxcgxggetjpsfhrszardlawlifgvtcbxmlflnglvrrefuvhsakesnttnliupqirkuubwvfwtkirqhdryvrqnomciexsatnqndhekrix', N'ucuejdluoudgebnvzbxkjithoqwwdmlmcztufalthlzhzqvhryngrxzyriodwbzgzgcihsbukzbphpmdcinpreyxbqjmcrsrtwgaudburnlmsxuyaqbrwfxpbukcgjbmmyyfsphrkrileawvzcglhecgivyjqpqxhmwrytfudbtwfywxrnmbvxvlfieypcgdqyssjely', 0x00000000000000, N'ppwgweeqtewqcocemehojagnqkgdhefgnzganymjyysjeianpkbmhsscgajsfaasbprulhmhmxveelmyulkuhcaokuzbwsdcwzdz', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (24, 1, 2, N'hxemwuuzwlgessxyggbaivmbgfrneeqonhqkyfujcwebwttynzfhacptnovtbqixrrplwnvrglivrpkjijtbzhglhgxrjvpewdwyqwahoiww', N'hzxixmbywuqjfoifbqahyhdvtwbnupwxkvysxiecawjysiyidtrrloxlyyvghpbfeuplgdptzomdkpnsdqegzrcyaaxtsawszihuxgnertximaowrogafvsncqgfrevefthlzaxgxtnjaermjjmsirjoqwvdgeecespicuplkbkhbetzfedycdkwzezmkpzusjcjegof', 0x00000000000000, N'zhyvonbtzjychxzggqldyobnsirbwkssjptgadatwuxowdlidkviqlylyiepssnftrjfimcxjremtuijygrdhlfkvzkojezfmytd', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (25, 2, 1, N'enxriwqegbazijldzcwkmltmywzdcggpwabgcqznjnkxupjghehpwmgbhygsoisvbsafjvnxgojxfkhdvrnkkbidkdufhnqsukbwecitizqv', N'mcpshtjhqiimxxlwricypyzurbaxfjujnxtkwmqemnvzuhobxzxrkctgnjzlrdhmmymydgolthuarbsshbeoulgkcfqtpnqlgvvwsfesqitgngrlznsqmfgcxxnbpryiihtqvfmqetqvhadlipgxboidmbykkjvftflifmixydfwbwaszubedzgetvdqpzukjnapgbpt', 0x00000000000000, N'jrnnbmfauentipihsbbflxtmeatofcqnjywzazklwostxsgzmywlyhawyfmmsafuxkmzdcxfcsezmxdimvthodxdpgbfvgxjzxmq', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (26, 1, 2, N'bjkazwprepukdqncuqfydduhngcwmrzctoxojvqwckwlcdjmlqcggwkbcrokwhscjodifeaytpxcaemnujpfgrldnzkbssltfwmouktxovsy', N'gewnxobjhhfmsnbkjllxajqtpvvwgkgdyufnrbrsezeffojzxkxawhubsnbwpxpdqkiynqtspdccejzuutgcmevexfehiqbcveonvagcvicbivuntkeoevuujgptgynzqcgleiuzsojevysofdcixjwzzwrdjonrdwvnihiehaiblqvaqphstfwypvkkqgnafzmdwlvs', 0x00000000000000, N'gxvfmuqwrytuffiqolvtmmbnhysbqqzczftzmbwgmkzvptshzgptfatcurixlkgiutplmlhznmtrmpevwfjbuwubikxpmsgrmzpc', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (27, 1, 2, N'tuexgwxkudnxrtgxlzowbllpeqkssmnyxvzokqahfxrjhgkshcgfqskseqajwsmquctwibjklgtcxnvsltfjqjyuupdfwrpqgmzeyixtmohb', N'zognnywgqznmdolgkolqghtebujrfdpjeyskjbhnvteyufijedoihpfzsyspoygnarmrftzadwpsshcntpwmsqolhecohabqqqzamuehemggpgrujrjydhagnnwkasznypfryyubufiiprbwqlklmniqzwddndomwnxckgymwkhhutnizxaflqzjplcvvsskostathwo', 0x00000000000000, N'dbepluuhmovqyaqcxmtueyrkongyrolbiimgrzdwwmtvjngcmunszhodyoirimcagizjpaxpdslbqfhedsrioihdbieevmwbggxz', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (28, 2, 1, N'mvujknxizmfhtmvlxpriahilaepcrwcmrizaqnrkbpgtrjeslwimzyltsmpnspdeyhjpyqezjlwiaeajjdmtmjuzlkfguqsoumtlxarwiuds', N'wxmudntjkangqtbwbaemdroubrdrrifrecmiellwdxuedfcfuhldejspjewwnqcsjbctcgkdqsiwcjiicbxesfqnjcdfvlwufhvfmqvpvaxbfgpelaftwrhvxjciztrdssuwwrgicwbeebinabsuetmhqbnzvfjsfqrclphikeyiluxnlcwpnxmjiwfpfizkzwojhufp', 0x00000000000000, N'wqzxeqdhxizghpzuqgfukqrgsthzgzygyxjzqjlzmhkxdwrourtidbqnoiismowihcvonlnzxdeuvjyiglvjdcuhkhkndcfgxlgd', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (29, 1, 2, N'apsrzdsgrhdmtxkugkobahwtaypkclswrljzbjeunupayzppvohwyusxftugvrxiotuwqevidxntxflavyrnqaictcwfkkvolsnzmcxehjrf', N'qgdgudgwvuysqsukwwcbqugmslkyzxhfzrrktqccplniaodusllcwqvtinouzptywdbosqeonunpicwnjjbflbzulwuomnrypelyjzkounraimyusfgqpdvrcnlmegrjhkaessmjvdsxfbohhdjrpbumkdmjoocbvvuhlwtmkkifpmlymhghbqdvroceonxgsruffuis', 0x00000000000000, N'ppwbxkxuwusqdkkszlwquxzerbuqbxybkjdbtimwqmpjyrefqywynkfiuboufhmuixtnmadlmrxqokavbddmjytzcyhkkvnhuqsq', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (30, 1, 2, N'bkjanpvwwxesyevxwsalllvjfvfnfvtkbitfkbgxzicecpretetfsoxvzmkfjqedignnrsuvznsovesptwfsovhcjdhyqazqdkhunvpmgfzf', N'xkclvjekphtdkacnakanicyhlegbydexkjmmtggtussfddxqhbgsdknfvvksvotbgfpzneafntkmdgviihztuidhqxhylvhswpnuqtbngiodntljfxmnehorpyyakliampvctqtsipyvvlkfwdbrktpiblhngmuomvwhrfmprrgyfktyydqvqdadvjctuxzvssomhpux', 0x00000000000000, N'kdtcpvylusshnjaqpqvglcuszsiyxqmlgjunbhupqbhlqaildwwnliuawygumafhyujdxbyqktfjbrhjihprhimmglppuxmbyeru', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (31, 2, 1, N'tekdqrxunrbfbhkrtahbyjlnbcfyguasnefalmhkmqrqtvkobarvllpysxoclhhnxcatupawlqddhzhnsaampwggnjyiciycwgwpnqvorejm', N'ofpwpqkyxzdwggucjsmashsnlqlujmjgdnanxbzqyfofmrpnflpkmtkbqifujrvgsefyxkeqngwfpbqtopknkndtccziaetkgsurlixmrviurdzqywjwgqmmvstzdgqzhstzshjmymfzfuviocksfolavusnwsrxyumexaplzpqpdiqnhtdkfslquvwksxrwqqnzzjyr', 0x00000000000000, N'mcalzfnidacvkxzbaumnllmvjzccbyynffoiwichpaclnuysibhdgddtgipxrdvgynyscfocmvnrlcfcozrlzzctqjopoydnabps', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (32, 1, 2, N'ultubzvdqybiqfoqkdjrchaslipyjwuilsbyyjqishunskllvzebuviejdqoehvlyaskgmpvwfthedluuftfuehidqsnzwjflzpckfoehgvb', N'xfuqfrhorzhpgkzeeomamleesvfscgnabxcuwlbkirnybxfskutrvwfpiemntzfkjpbtfwmopjcziafvtqcihzhitiltvdieczfpwvdyhjfgefswmxcxbzqofohuczdyairehqhnhdroihxbzlhiqioxzkkutcnvgjlojjjetnfnanbsgycwcsnfkfssfjmhgfkztftx', 0x00000000000000, N'ahtsqydbwrwsvbzyzoooujvdcwknbtlfibevhrvhjtdilgfcflnwiucnsvxuezcguylublheromqhdksedvaqmjmipvitwxpiila', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (33, 1, 2, N'tifjiusrcqnoeyktlvjbumdmfamkpoabnmxygptxlbynnryvdatudtajcoupawgidmtdreacmzjskiivwxehtngtteizbsbyfwmusffukzxs', N'paohbsbwkwnbugpkrkigeykglfuxdpppwktnafhvmjtasuoymchfxrdwdzsoiornqnllpzigboobgfxpwgmhmmqmjswfzvhbyxvbesqlgazkzgqcjdqnkntltmyurjkehveesjnybabvcsuwfhjoyceupjesxjvkgqhhjulsraeunsoistnzlguosuicfvgbthtzhypw', 0x00000000000000, N'ncfxumfnzhjmahdeaxkbsmzkzhjaeoltagoqaslctgpniglqtdjnzhmautcbojiyqfugcnflfessdwvspbknlzjvlntnmrayoaje', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (34, 2, 1, N'xmczabmkkpxvzoasjczdwhnoyuquzgqqggdvmfvbujvwgcjiecvvrychmjnraxyllnbdxuzljzqmfsafpewaaanmfftdqnryfzvfdtjxbzmk', N'ntujymlyqpjghwyvdffscokckjfhulsdmcksozrvjpouctbgwitrbecemhmxqtbsnzyvemjwubsacxzkzxpmrrhptclsgfrfoeedhnapsrbklhnpdufzykkxvqydfesrizglznohnroyydpcxaeirdqjncdjypjqxtwiewmkcvpzkypnoypmwztxxeigukoylldyktwk', 0x00000000000000, N'fynerzrsegzoqbidwtfuxyhalhajlrrkhgqztqururvwyimmubthamsnhiwrhgsxuhwyjruprmclopllrpofkfbbcrlviwgbpjwl', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (35, 1, 2, N'fwbxpikaedcfiejnylmvaecuxxnfsszvrvrziezmgnnqcazowffbqctfqkwyagsexwxidznhxlfzpixskuvbdfueqkafxzksnimybstowvjh', N'srpamktjntxvfcadcoivflgpokfzyhczmzocblsjrgpuaiqnkikqggqedtihodtfhhomegfcimteeixzgubszjwyrqaqqfqxkfsltopspjwtlznqloqthqcnslxjugxhwawxqoaspggiztadmoerecivcxqpkqjfuapfcfrsnkkspkljmsjkgcgbjslgicfncefstjcu', 0x00000000000000, N'lvlxodwzpqmcpcosonljusjxzrwvzetyvtoiajsidatavmnjevpwjbzaovhmlgtzbcjffkbcqvrhrgfxtxcwvdqvbtdujlheumwf', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (36, 1, 2, N'nebtfffpsfasdckfhwtxyxuvdoviljpywiqayfhzjpwyaqybenmbglqatcexjvekhooyegnmatjvlzoejazncdetirxkpsptqwkhfwmgniot', N'auonvrchdnvvkxpuwfjoseoiltrvnmajszqaqovhclwlltcujaycwjwqnqcvoormobkvkvibeabzcilzosmmiplgrususdjsecfihlcifsbuwwuvlbztaclpvxuusahihpzvysjllnhjrjzorfvrqtacdjdyxsqfkbfmcwrjaypivqnszeqyoqvuclrprfvqegznffrk', 0x00000000000000, N'ojfzbhrwysojynsybprilsnsrelzlpoywdjzhrcqubbscxxfrrmtrszpmeyfelommejnwmycliygrbuoypmpxflilzzxtdnbwqec', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (37, 2, 1, N'ovpklfrmpfxzvfywohynnakdpvvjjrbmbicbybejmrgbwboxrmtwbkutolzeikyzosoytbspuilmgquozbartayfhifuouxkldnkcmxqmqcm', N'ovoibubmgrbpxvhqtbilhqpgnobmqadpnlgfcpfbuwlibjzrdfkaiptqptbhnfxupzdepsiccgghhtepxdywplvwdemmcmiluyutfrhcudyyputuwnmomjydlfqzomccwrywrexpqtsbtgxusghzyuemcjdlgfjpcxebckcfkvtpsnbjomwkwoaobsnvoqcvtozlipza', 0x00000000000000, N'irxalpurokidkbpnysfjesblddzikrvngkdsvjmwsifnfewifhvxxtfyftbjykrqiizrabvgpxisdxgwtrcmhrazmwdgqwfpeoaz', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (38, 1, 2, N'rzhwflcaegtitbqnwqjvzylfmpmxmzfjomflbkvplnzqhyxuqlelxiybiaomaittilrbfaanrmdtktnityhjwxibhxhsqebyrgzqddiwyezo', N'cwiwymytwrmtakytpbvfxerknwiwfyoezbrmthuehtdgbngwetjgiruoeqttysffrpoxqnrmzcyueutxrkkzskxmpzbwnhbawosgsjqzfkhkumvvqhduhcjageowdawbpwzljvunmppoizxycyxjqntafalfgcikxjuvbxpxavrdwwjlmtmlcdfmovvswujznekgjdvk', 0x00000000000000, N'ymcksgttgntnyafozhdcpvywziiriehgyaviartibrmtrtcgvkwqhjelivwihzrivuoomgtnczkisttavtaazrkiktqvreiujwpn', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (39, 1, 2, N'ztryprgjfuamigjhvtjnnqqjwnrmpadbwnrjzxohalmjzzobmuoosrtdrzsuswovsftoobcaauvdhrjjolhadvsizriamqrxjmmprwwmseny', N'rgurckfibibjzxyjddhegkuinnoemlbepykvdaerqalbzslwecxliruopmgxfujlkymttvjpvfgtpteiyxhenyvzioqfxldluabnihvaxbcxojcmjkovqlzfefdjtfpyyqqrrugqpkrbfzdbguqppdamwtedajungbcmdlxflaxmrxqlhtqckimzabkicmrfqfjwrihd', 0x00000000000000, N'mzjitxuwmgnshdcnhjtkgqsfqyalcckowqdgnwmkbgskzbphieducpnuerfnbvnnahkcxvtgwojctferpkhvzubrdtyhpfhbuvzr', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (40, 2, 1, N'ramkglljyopbpyeqydswcczqhdevpfqfbssixzbjubajrgjdtvpmigkumyskvmnnwffeelapwxqbmmlsrzizgiqlnbzofifugkipymlgydvv', N'zlmnqhgpfipbfrszemxclndtwzyskyfsmgvjohbosmgyeufedpnnbhyahrwgtvfvfvvnikygvsjkiaujaxzkilibadmmfbqnhixzpcjslcaohputdgzuuapdmgetwvkmqvtotdomordfnbcdjhilbgxxiuiylxaeikhriwfyiymbrkoqosnjbpfbnrhhvrmeeddzwxgo', 0x00000000000000, N'fqmowimwypjmbfygcwnzadqorergdbauusgrsyccazqifhtsvpfetmrxlgzbzaeuatcizzqzmtegifwygqeysmjdihlmypsrpqut', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (41, 1, 2, N'jjdljezieetwxwyimiplfgcqlqzjaqbzewhdhmxbnjpqrezmfzadouquespytyjzpifwolbeeyntwnafncibdtiuybxkuljlwsclowdyzsus', N'ruhchbttgjjkzxjrmcxuuxqkfkbnfnpmxpqllpoaiamhsjvamfzvwezfkvzhmkhetthlogmuurdzopaqozzzbklfewhjzibrzrxoppvtigtqtqdzthuxmpooribrqpdnnpnntnzfdwgdvyktwfjpbyfexqdgelpjoiciddzqsiqpihodktjqwuwgubdwsrpyzpdijmee', 0x00000000000000, N'cfxhnrszfbyprazwlznkbsctzwcaasntvtavwhwkdweracihsalqgkivpcfqjptvbwgzzfkvcdfvgtkvnmkqhiddaelqhcgajxvp', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (42, 1, 2, N'nupskofkdymvulqiksllzmxevrdcvazakwlwxvgbyxvsgtlmnrwfelwykutlkgnznrjxyxvtxgruwfqjsoatwkxjkjfcsboydgqqzgexlyrg', N'uuniydbjmxjoyzcpzjvjzexcwlsylikuwxmpaqrinrqxtiwbuldxmzhjcouoiqaypzyaygwjmwknhqguwrmfgimconsdnjiwcpscxarngtfxcgahgshxcslwuqsmrhevpgcanhtblectbfzhqdrnnysrbszxebfmqwrwhkhcmxjmojjffyudckrbkmbtokptvmtjebwa', 0x00000000000000, N'ugmjkcmvfvilscjpzorzohrtoyiowyoantlqhyjsytudphpbitiyjucrgdzharkrlslonipjoowxqoapcwryojaeyuwoqrkppbmz', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (43, 2, 1, N'atfvhfwhtuvsyphzslpjmmgalnlcpxjtbmbarjhqljvdnxizbgguzeounrvrqdozhzshatrobnnrmunwxjpzdjeqhuivbngczcbrwlsitfdp', N'qjrpodcgjtfriupmqmbsvjjjbulgmgkobgoguqswdowttmknmoatpifbunzdelvhsyroalrmsejavqdasaprmmtedjoxdhcqyumdauidlhbugtzuegdfqnqbkultpdsdxrxivgfczbbnhthuzkcygrrfitdqpkjkjswndeosfwnsfzxluhntvnatxcmgsiiktpnpnjco', 0x00000000000000, N'wqeiuhsyeffjscxasffnzpnvewzogwnnxxhyiywktdcooqkiizevfzezzokzqylztlgydqdfcspwhycqypccmpbzvbnggctlotad', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (44, 1, 2, N'hepsgdncyhccoglyzyqwmwantxdcxcqukfiheokncrotlromjfuingnrulelubciukxbcxqqlbawibrcojzszukmwrrxmjryzvhztpxbtbxv', N'orbpdvyfwqznhabcqgmytghgcjyqrmdphgzlnmeesroqrvwuyjmjaylcbefunyopwcoixqaednqeghbledynrczqtvubwkuwcbpkyhdzqjwjbguuoelgceajpubhwbfjttkkibstybzrfbddnyishhaulqpzwzjvntbicjktcnfxgvwnfxntnmyusvrwxerbocyqiyrr', 0x00000000000000, N'ipruasrmbwxovzatywrhxvlhsskifemyjvbfacvpwpdceynidvospnkywqlmofavwkfrxhwrllepeynveqvxsjxubnliaawtvyiz', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (45, 1, 2, N'itvxiksbdwubzjxwbtnzoccnurcndrzjrbnmfoixkkzcesdxcwoarpkwslysetrokcfqbfsisraazoqjmrhepnzmdzyllakuqoudwhzlosdu', N'efbeudqscemjqzydjymqhhdamfbyrxpsimatabvdtmmvqcbbiovoodohfssanvdfqpgxktbsxosrlcjbnwraglunyidvibuuxlitxctogttmjuwzubebrekwmbcwvtvcsnsgwtqerbrwsqeuahnozxuccneyqwzvgdguvttattskqeqyjgzzbhxaxgfdxcfzmzzdqhmm', 0x00000000000000, N'dicazmnaftwmajxumoqowngveoalisvbjqamrylsehhopcrvsyxinlnsknainxnjmpiohdccdkpdofgunhuerykjxfxknhmfmmew', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (46, 2, 1, N'rublgmuxagktgrwwwosisdwgxcxwkfxzjnfxmrvsvblgnnnyaxprvapgbzpvlqzvyurbyxelemqcvnpmpcpaepbpvxtvbnxncaphsxnfwhxz', N'plqfhmcagqgsgwyimlvmppzapnyihosoniygxisoycekfsrmbygszpqyhcqokwjyeogzfybcgzonraccbhfxzelnyjgngaxweqjtprxykleafyrupmytqnhhrxpvvclkdsdhffzymlrsidehvaylwxvezmlmaekxbcocwowrjuqdwmcqpbzeuvkhioruixtdwgalgmai', 0x00000000000000, N'zxbzuifvfcrtdhjtqumcbjieebpucrtdemfweoieszqozkiyfjdtefvpxxxzczqmeafzkesriwgdicgxgmhaqmzumfbesnbwkbxu', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (47, 1, 2, N'hrknbkxugmtabryxyulxzcyzfkkzjgaylyaffjapgokfpbbwzkvwyjxaoozcwksgcogttqfuduzbfrdhatoejkvfbykiazfnikofyugednyq', N'bwlfazpiwcvvyeamygzpuixgtkokhgrxgqbjswyoikjaczhfonekfocuuffpybxyrtahrewcrasggsihwibrpuoasxbeeynfnnlotikadopftgaxxcwveaysccruehxujrusixpnplhprftsdzivhxmemsbpadrqnlwarammvvoadklazwgrsdocdnepihvgtnpxjowg', 0x00000000000000, N'anzjvuixtgxgbsfftnsddffzjtxzudesgtohihflftpsjpbqmgjqkljllylwekywpanilopfvsbjbidauksuyivbdujpnbugsdqx', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (48, 1, 2, N'jrzrvnzsatufmaqcsphwlnkhdngosqhabtgswnvzqtxqflekaucjlasvsaeyhwcmgjpzkxsuxyhbankadqzbbgjjgklqcrcholjojcwhzdvx', N'ebdfomehgvoovpripgrvxyjgqpgoilkhpyefwszlcckeioyypwkvgkezpmhfhmfnlvcsehkpdarcluvwwmejwmwxmclzzvdlxsovzjpibhynlltnzdviyxkoglcahqnezonjgkfvlvtldcmnfmvriprypbxvoiofxnpyfxgapmutemnxujonexxuoitpxvqkihrmqsbj', 0x00000000000000, N'zyouqlowiogkrlhoasnfxgrodujvfiqbahidyprjqvxsgetayjwytdgdgrjzsrnkqaaeujuwyjanoyaebvqbqydyuuvkkvvdwrxq', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (49, 2, 1, N'grervzuidcrraejwfbmuofbcqfuslntvunqatfwfbbremdyanenpzohneidulbabwblzcjpwmylfksybmigwxbwhpfguxncqnhqfcqimanzk', N'vkbzjgshvvbokclcnpuxtftinmsffofznogbjbxvfuivgorxsgbcjibpyuhdrkaykheskiqprwsdseczfyonbhmumkubkaibjfzkwzyzfpagqzciervgsuulwgrkqueyfgdeczescjdsohgtrhevaloteipcjsiccfnaqwdzoxcdlyqxgpkvbhthvrrinxeesbnvmslb', 0x00000000000000, N'runxzbqjlchaockwalctneygwpbqzicvupgmdwwtxrtkeezoztmukcwuculjhsdzvfpgbakkmnhidrznvacwsdncxxjrgfxfrmaa', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (50, 1, 2, N'booscgrsrvjyziidgixnnxuunxzrqifogwjqfdfblhcvgbokdjfjsujzlxakqfuspamepugcfjcfbbxadtsnlkevzluzrdpdgdykgmnoajpw', N'brsnwwdypidltynhwrmtayovmiraoskokmwhqhehtveydgjeyjawqypurcstgqtxkbkjnxnwvlgpnnbnsjatmfmsyqnkeqamqszuvsijxidtwaqzcjfrogeiggfygwnnxjdfnrfhnetqgmvmadjnmvhxeatfeyxaxikgqvwwcoyswoidcypjdhohybvzqsrqyclozzno', 0x00000000000000, N'yfkrqqtwgacbjwdfjwaucmeiqpanprlnaqhmggpxoeucijdrqlvfawnkzdyeihqehqjnvcqphonhifjkwoneoyhavrdlwpusiehz', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (51, 1, 2, N'sixgrqskkgnfpbvhhntqvqpvjqnpjibflyfvvmhudklaqhhbetlhicdybqpwepzqskziltepkgclnxseigemfexxpswepjosgncxgdknrrde', N'wlkwnuiphhhaoqjyjjjabyqllmghgyaxzekgwwrlkysyllauwpselpsksdgsvgpxfbzhdtozcnalzyipazankiioyxmrpoqukdncwtcyjgyvvkrckaxqdkqwfisevrjbduvznythafadyeeixwsnthshlgchmtxtosmqjxzlmgctsthiwuaikylcgmqowclwhmjsynnn', 0x00000000000000, N'qpaxnkwzjyrvfdzyqexwcsnaqqlvkvdrlwfzbshbebystwfkketkioaopmavmnmvoyztfmblizaqsezliprgdzwwivquajxtybma', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (52, 2, 1, N'hoexruyxpqgpdqlmyuruvlrufmgwfdsjlnriqczxnjejmstkkvgngomzknbvwoozhtnomhuugfretceohcadfhdkdrevswkkpxtrstlrfbtg', N'fhqdavxroamfrevjfwjrdhalqlswllfhavdlfedkrxfbtigktmqypjiburnqsmvrqpozledwachzgypwphwiapykfshimmchvnnunfflrgsnmytilwdzmbyszarwgcvzktfxomqfmeteryfpjoxljldsahbczfkgluntiaoacbstivqfqldshgsijpkvtthvstykbyng', 0x00000000000000, N'zeymhsyhcqrgobhvtlpcgsscjiluweuehtzapplwqzmlxkapvvnilbktgskwpvdifejucgfqhugeuktwdavrttdvtvutyntymkqw', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (53, 1, 2, N'psogaqkuzyipdjimyuydbrjpafotegbszgvalmnsauvltgzfsjqjghwvygyhazfumbelzgastkaxzliiclreiymnmsetjcnsdgyoomscwmil', N'hppaujdonsphnexrewihqqxfnxjbyumhzqoagisajiwmimjpsylwdmvrpcrjzlusyqchyhttslwtvygdzswrnxyfdnnhqwbkrjwtyvlddqfqxynsyfiqxoawrlnzpfrfkporitllwnxrmuqhkwmuxlozppzhjjbtqneogxjwmhboxwmwgmexugkpejtcstojbobtgtto', 0x00000000000000, N'kpzdnzyxebdiurniewocxngkfodutrixlkxweptcxyeqzzknhexmzgxjoptjjpziimhgzspehloayeibnpcojmznkkwmosepyygk', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (54, 1, 2, N'nsxoekfarvxtoqfupntefjlwsoiiskgyttnqfelkhupycjbmaqzmwrxcfkxwtsfowglprgoxezwmgeovuncbtvhpqzzjedzbogdcbzyxkrmt', N'gcfilvtpfwztlprgkgsjehaiddwaiukjjmedhztnptnljqcqszfycemibxwsyuxrwnbrbnxckoomriygdotrjbdpwypqxpplkbbjyknkzasiytwruluwcsedqvejiorvnqytfqvwbiqtpqkrsyppfynuawgpnxqllxblyospfgmbysdbnurquwpwbcbgykjihzsbjjgc', 0x00000000000000, N'hfchexmjybvzbvsdmusxhrtzyienqgpupvvnnbhptezkvhpgvsmuychjwawpqogancamfogibnoxdirjzehjcnoamsvjalherwcl', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (55, 2, 1, N'zvxaarypisztklgrcxwvlsypusxqbpurymfacklplvvtxsnehnkxbcuzarrovzlalgdwlnvfobszewmuialssyzgcewduomynbgwvwcwvfpa', N'uuqgqtmtulkylngoivjggrtomqfirprorulyhyctsfurebcmmgxvzddehibcfcegzdijwowscplsawqfpdxaegesufmimbhjsafimbpqjtnngyxwluhcfrlszltvnvqnggmsaaafecgyqfgyajdkaxtlknzoudjnkwcgllfozfwfrcrspgrmhhiwflgweqghktxqcyiz', 0x00000000000000, N'njfjvtjyaqzfehjypmpymlodmuxtmdrbnmxgwfxdaeznprcjcetwbxfmrntdjdusgzehbidvieblnpgrwdicjvplcwhezcgcsswo', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (56, 1, 2, N'nilirmwzcemeeagghuknuuwfpaawusfseisatoyszrkipzyabqqrwvsmzfwgkugwkgxzgyiczwfpovjlvgjcxeqcagfvwkrwzxonqhrkmwmw', N'lvfxblellnmdehbvjpnxxhejndqmjveejgyzhavserflfeockslztprennrcyylkbteaeqtslejkigveiunvqmutlqdtzoesgqaupttxosbviegwgokmilsbtznjlvarawhzltfzsavurdgjgudnjnpxtdvzskrqyvrntfhpxstoyxzatyjhhcrguouvotdtufmujdbv', 0x00000000000000, N'jlipuxqxvcmvegxyhjglmixcdxgpwmctfdppxbduiihwttrngffahvazmvrywencyfgbzsookwhtgtlipmunbktgxqrlrxltjoso', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (57, 1, 2, N'paruhnlilkpzouctcluqpapxqzpgukyzqfcffshvngmudxxxepwuuxkpufqczfcloziglbkrksgoqncmnhabfzmboatawoctrjiahzadrsjs', N'tarlpvxbltpjudrfkjtohjdvfybppvqkbyuvyvvtkzfvyfljejufhiurstiauffznailfltnsalbkisftplauvrbqhwllcdmjxljcjohtmqwwjnvelhqlpipnrwtprvalcqugomdqjcfoxsnmyxjzfpwrmjakiackwkddzglpftaapmdytqswytqxpddjmwbxarzphoc', 0x00000000000000, N'rzzspixsjijzkgscncgutmwwlbjgipjntuyacqahsohvkllvxebmywoablrthvecddqspwqqfygrcvtuhtzwhfcmdpnxvebhgqnj', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (58, 2, 1, N'mbupumgoadraqdldoxjkkcjoolmvhckblvwraqictjjdblrujgxgmwibtfdghgagkxftuolfzmlzgwbfyruxgypgcwchfnnhisdhlkukbnur', N'burjqujejlaluzpxgidsbcuhbonaedmnyulkcfemxjguctjdwovtmjduyuoywfpdvyxslmvalkmjixqolleftxrktcbgqszssfdeewliciwlcyaqnzapcenmawliyjlhoqeysnuqwtjihvdwufksnffchbzvsurratnknurizmisnrqkezlaxwucbsktyzycvvfhrnrz', 0x00000000000000, N'nkqifjmrhthabrlihgxzydmsmyxyzlehqrcrmtxrnbyojbxnhqsphkxhviqucfgrwptjjgbzcayapqjqbzwdzzgwalmlraanysze', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (59, 1, 2, N'uxcjjbkvdrwysifyrywbwnwmcizhfzctkflzsmqxpcedlpwgyoaqrmvfqnckunosqxxqtadvzkmomjmdqbzijfvpebhhpgnksbkhikuhaudr', N'agqmbwyceihfyyiqqthihrqitlnqnvccvdabdflhwhdtcfiswgcfxtzvsouzfanvbuosvlomdqvvypuywafeztkrpbdatxiuslhppwcjypmlgfrjpuzmbreemhnkrcmshkbhoipktfzyupshzsfkbvklhcqvtmqgagfqrezykloyuxncptrfyctqzrfwuwgrovctqxwh', 0x00000000000000, N'yugrgkgixkwsgzxztrokpdnphlcxnggupyrwonoigbiihjkvuhwazhgltzwzsrwlbnbrsjiknewrouyjdrxpndsecbpqqkuzpyzw', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (60, 1, 2, N'hinpzojvvskvhnjboejpccxctmnxiawfatpbivzajgovtlvvaacqtxmybvxychghkjoulumiwwgezyggknckllemgrluhgwdwjikxkpxqkvd', N'tmdzxbeleqllkpfiningourunrpavqikmjixczehkgtzplqoubctjdcczdyefrsjmfvockvaieguwgsxusgujybyjbsxsvtlwqszelcjemwevadhlpxdzerrpzucgpzfmclxwtknjblzqxbvtaoqxtpmjaqhagzuunxnmyrsbwzksntiqysckfdrvxnrwwyxtbjpxgmv', 0x00000000000000, N'huepuzoeapcdmmaigxhlcyeefiyrndhzxfiyuvxfahthbvpsjflsiugoqumksjvybtzhaklfxvspkktcblrweaskrdqqldduqzaq', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (61, 2, 1, N'mtfwkjhquescnarfyetlozthcoeudwwkyowxepaxjgbciwekdmxfexvpgwbpfuyrpsxhqtuvejjcncgctikddxbdffkfkxczuogsqaosdbfj', N'uuifpxxhyyuejhcdpoqidfycuqiaazmejfqhwxxcurigvcwbymcbfpvwaoyfjpudwkrqxnbxvenxsxwjjngasdkuwgcpnxrrqcjhrqcqqdqxlrzmvkyhrwtolqmlvluehajitxgkgobukevmlascewfjuxymqhsaountbldtmzmwntlkksqtcnixpfbyfvmnydndcjty', 0x00000000000000, N'iejkfwplavfkbdqcsuasfbzliecbqahalsxhpdslcibjboxgidiysuxaympcgmhyjtpqqbowrnoesryprlltwvngjexlcjaehoop', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (62, 1, 2, N'amelrprjbndkdezseoevifuapuivzkgfzaoxwzvgudqmzajpvuomkkycevxlsfoehctzcbtwtybjganwcmqhgxtiiamohfklosuimweejoiv', N'wyepzsuoyvrqjuswrzksywixzsetkyccktdvdsghnoepkujodifllkxscugfwbftrbjjzbhkthwqamqljazrnybeqogxujlevaidduxdprncpjvnugnipuwbthxjxyqqflyxufwdwrshyqlllgvmuafcxlxepjciaubsallfvqlprqudctctgobgrqmwltooccwlmjop', 0x00000000000000, N'nfptmpystlgxzfyijterqagslhqrjejfxlhqjdatzxumeuloxgcfdpbmbtzwzruyubdqgzdhbcdpvwltfnwtcbuuqarmolgkskpc', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (63, 1, 2, N'lkblpructxxgquetwzrgiribegbfjkkshsyzikaeticbtyjjhplkwqzzcxbzcrnbavhuoymoezkmaqrejkmordpealricxnrtjrukvuezthy', N'ygcuwkzihoqafjslpevxdjbtpbzmnlwixogdwzkdeppgyogjikrtcdnchpmxnoclqspjkspwxxdaijrjlpdrnnccbtzxukoerlardlvkzatsxdynjrghqxvzxhurtixrmvzruvrodhbhgxdmooulturngtryjernavbeklcybcxlkkgojtbvyfzulzcrdskfwxmadrjv', 0x00000000000000, N'lcgnalcmcnbhmkcxfruarrqkirdxpekvusbduixjmajgepcxnkivawceoziuhzimhcgklffegadmasxjghqeefqkkqyqvrcchwea', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (64, 2, 1, N'zdtqqecimkuxugtycrlgheuestzqzpzqncbfyprqkweknfauvjcjopgbzrfeogdkjbxojobcohgfgkvlvfheorbryotshtiqcobkzcxkymjh', N'seysoamioqqtdlesmointunkxixtfbapspvxevafkwlstmzkjglaewwdoefpyaxwlixlizygzpmcfzicaxojmcblzrfytxdncaygpoztgoyxtcrkthllmnwherjqljldjfsbkbenxpjmtoalddrhqqhjxmgpunogyxbhmjnsdccjpbflggcktnbfoctfberzpbyhrcuk', 0x00000000000000, N'juogjwjnglzkvwkfutxigrntekffzabdjqfbbneckvtxjccarvuingxyjqiumkylelllqyyatvjcyiytifpebopgrgwldcwctqzw', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (65, 1, 2, N'lgripwmrglnsdlambmubvtgwewiyblbzitshxaexfegdukfwkbrehrjpxspbnbrdvnumshtnamjytxtbrrtnumxdnkmkbwqcsvteucvjcygu', N'zxlivvayftlfurvjullqljpqkuqeafsqhbextkzclgimpglcmdsfsthycahmtejmiblbkqwytuwhhunjpaxitwlpitnxwqgfjpsrzomtmtmipmszuvlkajweoumfqdfilawegsqifpabhrafwhslrnwxecrkbpcctqdfvhbkpuqtfvugssyddkjhqkmqaljwjeasyjke', 0x00000000000000, N'ydcyfqtvwuthoemzajqjpuheaupykoupaamtkbfasijafrawmuacgxaylzofspzwshjouqzeuialwcwnfzyglxrabbiresgragsd', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (66, 1, 2, N'dbbfayfifsafsqkljaglslzberjdjgsuxebxrdmkzobtrtgrzlcncqtucgwhxvxwtwoahdrpzrfmkbakdatutopbunuvhtwicvphfgptmwjb', N'wgiigsppintvhmtpytgvlqtloslslmczhxlewtvfapqmqwtzqtsryhiitpjripwmlpnurzkauoezqskqqcpjuwfvmtrhzwmzdxweijbzzoaypspykhgnpdpwcmumtngffowrypmmjemddhsoitbnipzsttoimyfytsfpmnrfxxgrzqwvsavdqahrcsjbphejzmxbavay', 0x00000000000000, N'twqhqlfkwcjaaxbcgvmahhllhpzqpqkrvcqrxpypjkbreibxdabjbdavfudfmvljrdktqhuyiubtxglnzmxbqeftimltmmzikjzx', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (67, 2, 1, N'nzlogucaonvlosripyibhocwvzmrsukbnwrinfutiioxkmfzatkdqyvqpjfudyrbudslcwbflhoxxvczhtakxynsdwvcigzliazwlvsrhllu', N'ncgxedpcmpudfnvmbimbgaetpavbiujhxcbpaojrlcldbcpbgcwjtfvzotbastpcjmtgzupxkshcklwjljjwoyheagdmnoqaxwtjhqwcmyjhmcqklynavqcxhbrkopcchwwtobqsekozqfowgwgfywhgoemzwfmjemurmnlwbwbsrmjfqarfrwnnoazloigstxvmvxzw', 0x00000000000000, N'kyskpkmgykunivppyfrbkssievxtppbqvdegnypgukvsvesufwrcjnlvbcmwxutmgtghnajffsfobsossnzlkdxressqbgcmidpf', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (68, 1, 2, N'bxrtkjlynciytkiojhcccnginhonakzgftsluvcosrsphdmcbgysxlcqktimgetwfqkwfasgpwxfjssfwyikjxxqlgghdhjdddkyzuasnncx', N'csaplwcgclvgnlnlkkjaycbqfcglqcyrrzwnkmgvpggnpornxbxlvyujxdvqxwpqnkjqswunntogsnglmmslepixovgheoauybslaosrqgxendntaidcaxnabjtklgndjxyatltlgnhuoknxjiohdzswgujsnigldjqzjlaksuxayksittniwdelgpvpndgelisojyhr', 0x00000000000000, N'hhmbjskezgcydxlxqmymchfonhrldazikzqagsopbhffracuxagfohvewzwzljkjyjdwwxaegwzuusduypyjbfbhswsnpzoejwrk', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (69, 1, 2, N'whpomyvxprhivkucngzpwpczhszcwjvkfhcpvrtslmbcllabznyynvvfnlalsnjxcabadivkjcxorqcuxdiqmuvjynxkzmpezsnwizwftgpf', N'hspbjxhbqpamdqbpquwwdudpmwirvbboheugcnvitunswdvpincwbehythqsrvhwqsgcteyyhzcecputnkmknhdvkycgvitunovfjnmvkkcvnqyuriqixcfbsjeurbubyssaxyjnmrkrkytnjoceuagvlwrrojcufepbvobhsutedchgqhzmnnocpkwfhbbhtspaigpx', 0x00000000000000, N'kisopcllbpuwnuhccpxvugtaaoshorwirhkiancotrjviigqqfmubwdeyrtzopxpeyswkwpskpcagxwbmxtpedhmrrosxpltfshc', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (70, 2, 1, N'wrtzzilkywxqfseqnllcgxcbcodmrdyxxfroaywusztaetwmudsuqohcwtokohlnteqigyzeozqglknmirwkldymcezsjacxhgjlttjkpofn', N'cnsbtygmxqyqzlziesofwxohdsufqbfcdsntepjfapwtgdeuufhijzgmrbsurzeefutpaqteoetnqguaijwvlcunoybigdzvjsdopkeuamjdmrwwjhoqiwmvgvfikifuzssowcjoevphnqlokpckhvaihjbjozhvpghkhcnecvqgwtxzcoaxofhyfwgfbgkpccstkssf', 0x00000000000000, N'tyrucylhugziziezizatgvddpnjpnvrqpgbejovhulzclkcsjcgiusabsarjalximzhijibymqoretrvtjqwysimlnvebqrqsplm', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (71, 1, 2, N'wdzjqtqvxfjogwzhnbdefwvlyojcpxutvinyzzeaeoeboqiebfhqwdytghycwhgsqjjliucxsigkptpdecfrqcbopndxgzvdywzelvolxhln', N'peqbldsiotazwleyuwswozeffxgrzirgexcwrkccmomvnxmmwpcbgdbxcbqpfgchazipcqovrdpxnmxdxtstfvbuhrivanbgsrycvoakeeeeknddmptkcaokwkpynwnpavigucclylxjwfxpowzhklhtybhxqoawervukqxzmibudcrtyaafeiidudqlhxhkgibbfycs', 0x00000000000000, N'guszrwqjdcxlcionvqydxfprcowrruybuqodftucwffryamwvmydvjqogmkgisujyqemywirmxqdwmsjtskzimfprxtjialrdmdm', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (72, 1, 2, N'cokhnivsauepnncxpwxjdcqniiujtlgadogdtswoomraxzhrokqqayudjukhslalmyppptzsvsivusiwdkgayawqbqcytryfbjshjvfyqcbq', N'suwugwqnredzqwemssvnolojpzyakeumwhfhinmlgpyveebhyqxjaqjcpyfpzvebjyqtpjmenhabfqvmjlipxaahobbvdahbgdctabzqbunsfargwgyvzlesdggzlxjblyepqokymlyguzxdsrkenxfxvikzzezdwtadagcbimconijnkrpjingrutmnujbrsemgbpmr', 0x00000000000000, N'vpkdlmsiinreaonjppwlrbhepyyaivlzizapqckwjjrwqzdrvmdrczezptwxarqecngidvjjczudvdijsbkrlvbftslpqftzqrfl', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (73, 2, 1, N'bpbrdtvgyxmyhgvstlheoyxcrvvkklmsjjkcshqugiypzudxpqdtgpaphcboehfwmvglyonawwzuvzkwhspifvlbipxgbrqnqxcdeprkeocs', N'vjalkmkdhfdxttlcsnmduekymxoqgotiabonlieamrrjqjopigdfqhprkndihragmbjoszthzuprtyudeupzjeschalinsuswvxhghoaukqmgmnwxrxhrwqbmvtcyuwwwowmyykrhqitztfyvgcjiyjnvujoeynuwfngrvjrhockbmkrumsurtrznztotrwwjhmgsptu', 0x00000000000000, N'jetqbiornhrywoxbfhnrkgynmojtemyryxjmuoxqrdesyxvqzsarsnwgmzvmmxensvemcyvyrgzeahnkgntprzcnqzatldftqhxk', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (74, 1, 2, N'hsoqfqbbebskixnsczlqfxipxuoegqixduhkkdpzkxfoldyfycwelqrhmkqczqxjzdaojepsqrnbcjswdjybrfeshcuymagxqdxnobjlrxbf', N'saxjppyewbklnscxnngxkwpzctiejtpervikrbhbjqtaxzmhxajlnkcvrrekwoxwjkhqqnydwsuihxclwduhqvfhnmxuwzjqfiooeyjjyaclonncfndszhtlmfmajpqkbbyqsexmbcwpcwryaovwnaavakaizqtyznnmkfsuoohqdrooojflhzspwbrbowvlxfjfwxee', 0x00000000000000, N'woujgnvqapunhcswtnuyiyutlmnlvkupnlhczyppvwkxvtpvgqdkmyejwiszizkfeyafdkjobdooqiuvcoouybsisiujocblpvxx', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (75, 1, 2, N'ihflcghhvknoacawsiloxijjnvxxpielyqtbzmzmpyypiapulbxonlseljqclauuntjkpqtaswixuialwmryuggxvdwnbsxoscexndyoahex', N'rqhimcenqcnvutxiwugftgwcdmqnxryhwdvvpcobymqqozlkgaslicazibhtpzrxiyhfihfeqbllnrrxdrbmwubmempwxxiqctssvomojzfifsmicbnvnlhxgeezdhxyfhigcarcmwhupefgwidsvqiydesriizxzsosshpdhuuquhdcamglpdoirgyaykycbqvdcqca', 0x00000000000000, N'lrbezkbwvgmxiinxxurbzqyevdrmubnaoolurbklskmghkrdstrplxrjjxctnclwbeltwwqdakdscnvqtzylihofxqjqephmozug', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (76, 2, 1, N'kdtcdvbabektosbblqpunvnhwkrezqfcmhgfuavyxxqmsteshtpwbsuxkbqrgbotloslylsyhhmgupdilploewzafegjtbzsekjvhhxexteh', N'rckoweqnadlujvdbvjeiiiplcyheyuyxsuieavjqvlsuugnafvswrrvuarnonemajkpmomdxdbkljtbiqlmidbyiyiwoihsqvccgraztfdhrsfrxqgbcshgvpfusvivpbghjcrubmvpudoioxefixkkvplijqkxtrpfscmagdnjgdueqitwtxorixdquwjtedbdmofan', 0x00000000000000, N'jimxxwknhbbroyutygrnvswjuokhyhtndormvkdqipqzkdgxbejicnwxxtnswhdkpxmrxixuukzmfkneakssijygffrmhydotxyi', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (77, 1, 2, N'ldpptreellpwpwqcvjlekohgsxojwqftcsubarmialfyrxsnmjrmlznyejtinzgpugpqbdqtzpzfqibwxyyrudqakqfpckpnllwnkpdlxriq', N'qxyciciupnahcpwitpnigzttnxlipmfpnolqwxyiaxzuhlxabhrebjkxpkwtaxgresmpluivecdgauzqaoegrkusjxlzliienhqiiqbojfzdyjkmitfejnpwwuliamygbumvoxwudgmtvwuxgifdvakaiocqcfcntxceietsbgfobbtazqpkxxuseknkthbphunmrrdy', 0x00000000000000, N'mistwffzqvgtxhngaulhzntmpzxrrqqpuoywfinyxudaickozymkciydaqgguzojicxufcickgaazzasdgyavxfbmwmjjkxcmwvf', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (78, 1, 2, N'mywwwgieomujfvsbkwklghdjjicbjuchpqyavbwsyhgzdbsbtehtjerzqlrhgpriruhdvkchqbattbykswtocrrxhvdlzjgpfhxczpbvzlqv', N'sxdacirvaztciripcmutsipdifjufovmlxvyffsejikwtrpcfbddcwapfytqsjrqbpvtoijxvisuxcuyfxidjpjmoackktdymzzdvoaliuzlzgwwfurssmhpyyjuqbbfbbudaakjizzbegsmajavmihmxoduymeonoposzvamdslywhvpkujzrodtkunzxnoyscpeqwp', 0x00000000000000, N'nvanydifqielgzlflazmzpgmkhbimktnwdswugjrfugcotbosprduqouwgontuugmlztirjnkarfmxprpnjerkxovsubkbdedkop', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (79, 2, 1, N'vhmetnsytrwawlosvbrpspcvjqropvljxehpdtxzvtalhiypgernlsblfaxqsdldvrvpobjtnukisrjmsgdgtqzvojsjqqrrbhdiwicfvbnp', N'zxgwdtdjhmutcmowkmvxgkqdfidecuqvvdnyukzawtykyscwivesvnvicbwdykiycicmzrpkhzsitftyodgladkgxqevuhgbsyhyiqwhztwppfvkwmliqpzgjaqtopdwjrhiiwhbwxowdyfcrpzvifnyanfmjpuctawrqhhfigmjxzuzmhctzbipfgfigjlxnverhgig', 0x00000000000000, N'wrqnkxbhjazmogkymcetektwxqknrrlmlkhmatfdzuagnrmmfucsjivumiuzxymruwnjnhwbngdhmwgnbmfhpetthttjipcekfqy', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (80, 1, 2, N'misprodvvijjgdqnlrdoreokmigfiavorcewlihjpuayoapfnpwqabapspveufgficlezjqdwzpotbpgkndnhyablqvmimikdkwurjryjeap', N'whrjdleomcymtyylrmozxnimbfokpxshgjnclepzlhtawkhlzqdkillxdhklsocenxwuyqabstforxkhwvunddxlzfrdndbfpfxohaakntkfvqvudbfwpzuycczrhtnwcmlihxsiuorsvdxzylnpmgwgryyifscinbispzinayksrhbpwqhsolfgjcllmobdfdholvyl', 0x00000000000000, N'ijjndzbzrcuihtcxkdbhicxyaroemsjjejxkguhjqnvrzpnygwrevzarsjivgujrovditrixstuwiybyrfyozrkfiqovkqurexrw', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (81, 1, 2, N'iltjktnnqruuqimbsyuhwygdrdlahmyxgkpawndzxbgfptnfgihgvdhollelfboqapbthzncrcckntojjtikplwwvxsydyfoqcqoimaqlgqn', N'btfkfhcfdbveqxwdofkhgjhoqpcailkgnxsensbeaokoyhwrjcgqoffhuntkcbauopryedmacmlgqxipqkxrdrkezxidszzumadrfwaqwgtbefkhzhooqwnsqedzbrlpzcsmbgmjgyquowwprxoepydppsqjbaqbudropjmpdjizjklxzfynftsddlrmtxiygemwugvi', 0x00000000000000, N'dysgmggojzgvfhsnyayoknixdinjhnxsxgfujhsxrrkjigxtzwluilwjqhvnorvdtfywuyxxilvudgalouyejzfdokrmpywfxsnj', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (82, 2, 1, N'ntfhuubpfujudhitiswediaqfzpdmwprhrcliaalkoncgbdwetzglkgrgszvhxgbtcyqufolearyiwjrazvnyoblovtnynvxkpfesisytmoe', N'dgtzzrldghgfbbazqfbotoftefmlzzvqwnwoaahzcjcwydzjmfiterbfanlmunlhkseslhhhqdzyiloicgcvquttmmlltnjbmjyulxeyvczvpulnpgzrvrrmpdawrvrwcftezmwfuzxejjjrpsuqjvqmxcbimgnaazawmzdxidzzzebxumlgegnoinpdlpuoskjcdixa', 0x00000000000000, N'ylomiyrjeepggeygvveajurcaysjcxifynwvqavsmgxpolfbvcqrnefrzilcrhyfvxkhjramfnnhlcdnbjnyryiurevnrfbbffhv', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (83, 1, 2, N'yqjfaapwehzoiqhjeemsrtiscjkdruvjnnhyfgqnvnimllzhyenqockhkgsdwactlnbwsltlwhiqhfyguwcirxqasbedannqonjlzxuyjmlp', N'cfykeslmnxroizkhvynsnvcxcwphiiyehzczejefswxmxftllqfqrhmvqswhxyujznxpvyxieujvqeqcjwmfjokxmtfnquecajxwbxirodcpbyxmwhzmrtinnwcvmluypcfgpwqwodqwquqhnjvmvsgohvvlkuxfefmukqedqcsacrfvrnotbchdxmfakhvydidfchgy', 0x00000000000000, N'vhowiiyfmtjsolupgiugeyhezpjkjoyqtoblhjnciiqzxaohkqddcvkihnfsgrwfvdzbnutqirnispqepkwsevdymrbjetxwoiim', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (84, 1, 2, N'rdokdlmtanddztigamncttqbbviddlduybmykzkevinbmztpycgdlubishuihcutvvebflsrodrlmoeqrrswejtdhopfbsguddyvngtjjxlp', N'jpnpblshrcgnnmvjsvydbjwchupkweeenlukvvfiohnmzkxiyjvincrnkacssjurvslfmztoejvtptbowlzcfyajskfikjpctxkfknvtvfrjsburzjdzebuoshdebvogwukrcenfsopdkdrnvkvikoisczsuagplteaxtmfitjsurejtedvmrpyiirlliskwzomatqhc', 0x00000000000000, N'xfqgruyfyqmcmevhrmhcdtwqxfnxztzpdvjhxsjmnziakocpuxovzmhzpfsieeqlteefmocfyuehfdmqbgcthcnumfpunrtarxqa', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (85, 2, 1, N'fefayyvjowmjsxveruhyvduhxnogbdeyliqxkayamguuadqxdvfdblmhluamnnmtlugbfqteqvrflgrostcuiwaltonojhwewxbxegjgvhwp', N'revhwfmfftwsqzgkyzhuvfqnshmjtypcfllkhrnnxhytiuoykxlwtlubdayhfhosycmzznadywuxmshzmhlptmxstlfnbrbptetunduvrbwocytvgoxlefgyaljlkjyajhiyxgsouofcvxwyxtqjbxmrbgncfifjmopxnnrqikuzasmueqxdfysabprshgzybbsluzke', 0x00000000000000, N'ovieczwtnvooobohyzxerhviwuorjewffxbjefvqtayrtgtnnexspragnxllmvbsayvujueptgsfsegoidzffopuzggtokkdudqy', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (86, 1, 2, N'ljixybxtrdnoaqonijjxdoshkxltatbkjkspmvzsrlyyfegrcxkxefsfrpeqfchvjbikglzzzwecbcwxfphlxbswxjzsdpufxwkaoxjcdnhq', N'biylncgkifmrzlumsefyymlzwttscqzhpmyyybxzqpntsvavijliabkitvrtrozwbldepmrabpkrktqtpldzihzeowxktcwwbryzavkitybvmdjbokrnijvlchwsgsmnrjpvodcnyvvtvdrpourhumzcldahwrapdbwymoenjzbtqyfhkwyjoqbndyzzbuhpirzkruao', 0x00000000000000, N'jshybkzxprmiamotlrmtnbizjswgfgaopjvatdlhrqzxvlaeyaoztfxlhafzkpwcbqkwfwmhvxpwxmwzefugvxhjlqvbluuqulgq', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (87, 1, 2, N'mkpjsjykfhicyhauvvnoqpikvhvarfhdfqxnzooxhnejcfuspnuvtoafecempowpyyiswybtopwcwvzgjpeenxxyokevnoifazexnxmfsqsz', N'bwqiaaqfeoqsoujnrzqvbuurcorccfbhbsocnnqrmwvtbgbgpobjqandkassgfvsyidtyytcbolsybwulmitplsrszcsfhvgqbipoltbghpxjljpcerabvtvutrugoxepzsneqtwcfdjunpnxflpiprldlsmvbijfglbpvvkuneywrortxdmwsashchuarapgbilygwj', 0x00000000000000, N'bjejvapxrmdhtelhevesfawwzhymgmefafmipamcxrhxuxdnbnmxnndzjrbcsgtsmjsxyemkgsjtedxgyabyubkpzjgukttftudt', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (88, 2, 1, N'ekulyqyfxbnnuidbcoxyqkfrfojzoahlkquasxpmhmarfxmgkhvglsctpemxttkxlgyckunckpougqkgvvwqqwgjfgelcdejfvwjprtbktbr', N'xvjtchcrztjwtqnjszquozbiebkwxocgmskottysnpnqpzdrrltcxqrubhjupfznskvbxgwebfmmufoildnatpxbsgligvyhgohhgshxollxzeoihnqgqqddijgockjfkvwksuqszpmjkzmtwcbrtmqckmmgvcqcjmqhnozwigkbamjmxatdopbkyuvfrjtimdhnwgum', 0x00000000000000, N'otzggunlxjpgqmciihjkvabasyduzctczjjxhxmmddpspkbqronlhcgdqvkmqnikrdzrjvnruvzitezjvkhjlvwcushfzetkwuuw', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (89, 1, 2, N'pyeooyxqouujdgypxqxtdpeaqpvxieojsvvedoqvgyegubovzpmkxdufqgtzbssmwuxrfgmqaqkguokapjwljpvvvilnarsmjanmxphwqptk', N'equnybvougvftypxqokczxvtjwhnrjpnidzbdjmvxomypjvlbyeomsxzvztwaikphyozwplhgyetujmsaqljbgdzbxhxjuzorbqtcgucoovgtgztrbpxefayhmebsssskubxyxrvqimcmmokfcdzyibxionvwbnwjfppdiekiqlllkeruhgdihpyzntaemiqsamoazgh', 0x00000000000000, N'dfwlfufpijsnthfgjmqhnmbymqkxtbzmxucydyzqqwruxxulbvcnenawmuvnawaertgaqbkubqfrirvurhynepcdqwurgrkpojoa', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (90, 1, 2, N'ldbyzarkraqmohsmqlaexzxkboviievdretofxwupvlyszbwvrmmnzacufisdskmkokjmuyuvmqsfhipkhlbwlnrgpuvmrfqgfxqdygeahaw', N'taqxozengmaloirbxeafidporsdowlrcoyrrcupgxvrmiskwoebnxgwnwouzezwfxpfzrbrxnilikdrfcihpqtrzlrkgzyxsxcrdfcdspuhunuyxudzfktxqvyqfbpjhlcrhyhhnuefeaylstpapyfwznpjfapzchyefkexldgoyuadisdezbotvtazpulkxeubnozrh', 0x00000000000000, N'uaeufipylhljmlciaoeqcabhmtccakmqeplpgkabcbpvnagtfeaeuszulzxuzepvejivntedklotozlvkywsacskicajovauqrrz', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (91, 2, 1, N'geqruocisybqmuyasmmplpuwinngjiqwpoyhghpgjngnqcbtojzmivbilnelxpftirlwhjaokbfxrjtnsytldddidrzyeouxgkilplytwmfx', N'ywoomwhmqksucylpscguiicfpdfrgrzciipofuiyyeqpdikstpejlveizaxlbbpoovjmuunapxwktjarbbcsprkujectqvddzksolsrhnficfyyettyxssqucouamnqcvqgutwwqquphdvwfxqkijigejrvwnxkrtxmhbcnwsallmkrsxumplqnvfaurgycftxbgdmua', 0x00000000000000, N'nurvbsgfpygtyzvmllwqnvunbdebgpupizcflcoftduwkubhdvoclzwhfydoylaajqrcnuzeattbhuewbqgjcaudgyktvstwiztw', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (92, 1, 2, N'niqsytgqgrtvaflrdbwoedfrpsgllqmasyqaopgfvkgibbvpgncqpbxbvuahhwlzysyuamouhhyvmdpbyckqlvnshcprspjxluavxqxkwpgn', N'kmjxcqwpyptxvcrgvemcncfbtvknxqkaftuenvokplxciqcsopcjlmcmzsdsqdvdvflqxfuqdojqpferlhqtntsrpxyoudrzvtwviogsuqpenbjyqshhzypragqgvqhrxyzxcfymawwbborqdibdqfbukbewhklzxtnbkdhqcbbhxbsjjvpypfbhcitxojnkojvehwia', 0x00000000000000, N'ewbeplkmvevmeivlkabjkwpyvtnycyqpkieopsnwrgxscpuyvapuknrexljesbrmkapipmjvzoozhugqjxyctappnwgtldvyfxow', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (93, 1, 2, N'abybmjheutsrxrhacpdzdrqciultkjvofrnuacrifxtzqngkxokyfgytllguoazoacrcqdyfdbkixjcqjbnuqrtjepjlywbrcuklxfxnwyiu', N'sdenrbwhkymsftsyjvjuhyftubrxtktlwgleumaoiscyjreqcfsyqsuskenlgpxigrzruhsxrgcarqciirsrcqkruleernwpzhmszuvnwmebkcvbscyzxtoyjokwfulqbhxaylozetxdktywneyrlweelbdavnkycuakwymtqigjtbqucngaktxiegcbqqyyipztgaob', 0x00000000000000, N'ktpngzpdsulzadntlcrbpgomkqnqqtbwyoymrbredgewnbnvxtyolqeurgndqkapxtpfitbjcbqhmxugiolgmamblpkuroennlos', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (94, 2, 1, N'qhvriyvecxosatnqwtlsahopsdzixyjyvecfsckemymkgojpstakvaxigjhpcsgvzuxadiggjttosenkgoxuivaupjsekdgtcmbivccpoigj', N'izudyqxwcsmdkubmxdeccdcdxtosjoeeegznbfimrrixemmljaybfnuedkboqsxzwrvmpjrdkhcklqlnytxmjkudpliltjsxrqbjtczaizulleraqyoskxnntksjpdiixjegbalzovwglmdpwzretzfawmzhvyubodaxxuiumsruhwemecxajexsrqxcboipslogxbyq', 0x00000000000000, N'lajubkaialxmbewttqzycrlohvinembgmuwysgchgnapfcwsicabdfgjicqujgoiexdpmrivslctrzdfmisyvpqcuxdlzkspcbcc', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (95, 1, 2, N'cmxwwmiphumzjcokmmhougghpqgqmknutqbbydqttqgpriwjqtmrvugwvyhmspybzriownblmtppaqyytjgprwdevusqvlzrtwxkyofstnru', N'lbixoggcdstcrujofindejlpgjlthwklwxpzzayjimlxipcxlphznseidtyzsixverlldvosmnsuqezrzzfzsoaafrwygpqcynvpookqknzxmawdisqkmpuykrmqufrpafzmlzieiwkyibpqdxwhoctfzutqtjmbnfwjpjohnfhqdoowzlqsajzgbqumvowywsukgddk', 0x00000000000000, N'mnjvqpuedbowibcjhudsoujiwgautawwwqemdxfjdtzjyshqpagdmsvzevzmowalxoblzuwoahmwmgpvwiuxxdnymrudgcwzljqo', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (96, 1, 2, N'qvruxbuolaskiiddhncytxdedszkmaeliwyboplzwmopwcsoinbaobpxjzlmiagfawhewxkyexajktwqoydubwqvemverqpbgdwahdxfhath', N'tnjhleydothbcihurpouhiqrrbwoxmlvrurxtdgwoleymtvxlrjxzskxylcnspyonpxmlytbltjstawelojrsiqduyrltxrqyvpkdnvhwsojmffqaqnpuxqoequakztymadianvlghcwewxaqtpdlldcnxxbncrazejsycpuyleytfynadtiieptylgcxfxjwkybldou', 0x00000000000000, N'pmwckkmbtqxblpgecegtfpwxdudgtnqsszwssmcohdhrmcxkipzfezgmxdcpabogtbaxluhjfqeidvacvrecepiysidgwyrvlgla', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (97, 2, 1, N'shjoxfhizawdluccucqfjpafrcnljgmupkehxkfkombtcmmynksyxywoeimpqtmibeukwhhitvviciitbjazfkfnkonhzcwgsvnidqkmpymk', N'gwnqgvnmpmzqnsgtkzxcmexwxwngekhwkdqyiwxpqrcsdjjthxzudhvyfcybdiewmhhhcyztdpohkfsoierfupwvialrnzmppxgyyukegpulhrhwldgnliqgegoclwzkaicrqwpwoodntazpmdahavspmtexzwjmejmrsevoyqivkzzceskgwqcwuwpgoocrwehhfxhr', 0x00000000000000, N'bmgvoutfcxnaojaapoejuavpbaggjwbstvuhsywoozzviuqhtptboytcsytjtrnzctgwxysvbtgttlibylmzzlkhiivamzgdfwfw', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Draft', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (98, 1, 2, N'xuldgdjqfkdnxlhfdaprecultuzzsmmfuutocbtnrsgqoajzwyendyxvvejsbqktsjhjpluoriynixfqevnsfdnrendfkwhkprxnzmsvaqdk', N'halzxjfomockaqdgecszmisziuwuuoasmvwlxzmhgsrxyelyviqwgxthppuvebdxetwkkxvvmeujszqawhiomjgfnruzpgpefiztvpkvsdtqbjevkxtiaqeatyytfhzovpbewgwshkhblihbzsrvroeavciuoleoxahmwejrvzskcdysyexkqfmjtnbqndfhsmpgokxf', 0x00000000000000, N'voorrvysuhbzwcvnpbkfuvhohhttehqsgikkxyuaalczfjtxtsojfvackqvnnkrqshyhbhcezbdhjwihtapltnotrahywrpuwxbs', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 0)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (99, 1, 2, N'zgfrmbhnjhtjthjgqxckslqgabioiigimtzkcletbftvqrnlnhrnhglgtambxxbynfooxbjurpyfuknjnofsakivodwnacrxdycmqkkvtjed', N'cvgitrbsohhuqcicaekqeeljlmjhjtlcrxqdjosdpwvzmiqnlwlextqjllmxedfvabtgafhhauewosjtxuaofjrzlgksonmdvmjnnftwujwocunbdmfvpotovwfvjralmgcdmjeogbldeusnnaxcgvydswkrhpgfurtuclpllhtcciwjqqqxulccnptewjnhwyjugdjy', 0x00000000000000, N'zbebaimnmmvivokwamqahnytgyysrjfvxnlvxkcznhsyrlahjjbzgksfffkjxxcqfhxigrtqjxpeilvdgtvuxgpjbpvkhgopvmit', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Normal', 1)
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (100, 2, 1, N'kykaznietjcrlrwhmmqgsmydhsmolwezyfxinoeomjtqgzdoxwnijvqmjrkyqxpqlvyjnibajfsrrksuluyktkyocgubhrnelejdmkhrczyc', N'ubvszoofdshdrjjjoroyxkexocyonyyexbyynzdsslajgxdsvuttffkcyjageetncecolsmzyoiscxaqbnxonjxgvllajubzhfmaflvpshdgbsyjltotxpgflctxhkkoecwanfthuzhymozppiacyiyfbiqsgmgxzfcpnwawjycarwfupeqtguwfuchzmmwkxxsuuiww', 0x00000000000000, N'zzowmfoaxzgmgyoijdxafagcroxhvqothgvwzincspvdntacdfufbzyidezpoyvfxubbealwtmwumfbqxspfynngwjzmncoxhpwc', CAST(N'2021-05-28T00:53:00' AS SmallDateTime), N'Deleted', 0)
GO
INSERT [dbo].[Mails] ([Id], [SenderId], [RecipientId], [Subject], [Body], [Attachment], [AttachmentDetail], [CreateDate], [Type], [State]) VALUES (103, 1, 2, N'client deneme', N'client deneme client deneme client deneme client deneme', NULL, NULL, CAST(N'2021-05-28T01:24:00' AS SmallDateTime), N'Normal', 1)
SET IDENTITY_INSERT [dbo].[Mails] OFF
GO
SET IDENTITY_INSERT [dbo].[UserDetail] ON 

INSERT [dbo].[UserDetail] ([Id], [UserId], [FirstName], [LastName]) VALUES (4, 1, N'Veysel', N'Veysel')
INSERT [dbo].[UserDetail] ([Id], [UserId], [FirstName], [LastName]) VALUES (5, 1, N'Veysel', N'Veysel')
INSERT [dbo].[UserDetail] ([Id], [UserId], [FirstName], [LastName]) VALUES (6, 2, N'batu', N'batu')
INSERT [dbo].[UserDetail] ([Id], [UserId], [FirstName], [LastName]) VALUES (8, 4, N'batu', N'batu')
INSERT [dbo].[UserDetail] ([Id], [UserId], [FirstName], [LastName]) VALUES (9, 1, N'Veysel', N'Veysel')
INSERT [dbo].[UserDetail] ([Id], [UserId], [FirstName], [LastName]) VALUES (10, 2, N'Batu', N'Batu')
SET IDENTITY_INSERT [dbo].[UserDetail] OFF
GO
INSERT [dbo].[UserRoles] ([Id], [Role]) VALUES (1, N'Admin')
INSERT [dbo].[UserRoles] ([Id], [Role]) VALUES (2, N'User')
GO
SET IDENTITY_INSERT [dbo].[Users] ON 

INSERT [dbo].[Users] ([Id], [UserName], [Password], [RoleId], [RegisterDate], [LastLoginDate]) VALUES (1, N'Veysel', N'$2a$10$NSHphMXvV4GTd9G.dp8NGuCrHXTWlW2tq1bcGxLM/BpKuOFq/aFau', 1, CAST(N'2021-05-27' AS Date), CAST(N'2021-05-28T01:25:00' AS SmallDateTime))
INSERT [dbo].[Users] ([Id], [UserName], [Password], [RoleId], [RegisterDate], [LastLoginDate]) VALUES (2, N'Batu', N'$2a$10$MJafOM.c0EAnE8pcOp2eOutNCz7IELtac0EYJxdDFZl9ZXgBvP1wa', 1, CAST(N'2021-05-27' AS Date), CAST(N'2021-05-27T23:54:00' AS SmallDateTime))
SET IDENTITY_INSERT [dbo].[Users] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ_Users_UserName]    Script Date: 5/28/2021 1:30:54 AM ******/
ALTER TABLE [dbo].[Users] ADD  CONSTRAINT [UQ_Users_UserName] UNIQUE NONCLUSTERED 
(
	[UserName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Mails] ADD  CONSTRAINT [DF_Mails_Subject]  DEFAULT ('') FOR [Subject]
GO
ALTER TABLE [dbo].[Mails] ADD  CONSTRAINT [DF_Mails_Body]  DEFAULT ('') FOR [Body]
GO
ALTER TABLE [dbo].[Users] ADD  CONSTRAINT [DF_Users_RegisterDate]  DEFAULT (getdate()) FOR [RegisterDate]
GO
ALTER TABLE [dbo].[Mails]  WITH CHECK ADD  CONSTRAINT [FK_Mails_Mails_RecipientId] FOREIGN KEY([RecipientId])
REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Mails] CHECK CONSTRAINT [FK_Mails_Mails_RecipientId]
GO
ALTER TABLE [dbo].[Mails]  WITH CHECK ADD  CONSTRAINT [FK_Mails_Mails_SenderId] FOREIGN KEY([SenderId])
REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Mails] CHECK CONSTRAINT [FK_Mails_Mails_SenderId]
GO
/****** Object:  StoredProcedure [dbo].[AddUser]    Script Date: 5/28/2021 1:30:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



create procedure [dbo].[AddUser] (@FirstName varchar(50),
						  @LastName varchar(50),
						  @UserName varchar(50),
						  @Password nvarchar(150),
						  @Role nvarchar(50),
						  @LastLoginDate smalldatetime)
as
begin
Declare @RoleId int,
		@UserId int
Select @RoleId=(Select ur.Id from UserRoles ur where Role=@Role)
INSERT INTO Users (UserName,Password,RoleId,LastLoginDate)Values(@UserName,@Password,@RoleId,@LastLoginDate)
Select @UserId=(Select u.Id From Users u where Id=SCOPE_IDENTITY())
Insert Into UserDetail(FirstName,LastName,UserId) Values(@FirstName,@LastName,@UserId)
return
end
GO
/****** Object:  StoredProcedure [dbo].[DeleteUser]    Script Date: 5/28/2021 1:30:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

create procedure [dbo].[DeleteUser] (@UserId int)
as
begin
Delete From Users where Id=@UserId
Delete From UserDetail where Id=@UserId

return
end
GO
/****** Object:  StoredProcedure [dbo].[UpdateUser]    Script Date: 5/28/2021 1:30:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


create procedure [dbo].[UpdateUser] (@UserId int,
							@FirstName varchar(50),
							@LastName varchar(50),
							@UserName nvarchar(50),
							@Password nvarchar(150),
							@Role nvarchar(50))
as
begin
Declare @RoleId int
Select @RoleId=(Select ur.Id from UserRoles ur where Role=@Role)
Update Users set UserName=@UserName,Users.Password=@Password,RoleId=@RoleId where Id=@UserId
Update UserDetail set FirstName=@FirstName,LastName=@LastName where Id=@UserId
return
end
GO
/****** Object:  StoredProcedure [dbo].[UserLogin]    Script Date: 5/28/2021 1:30:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create Procedure [dbo].[UserLogin](@UserName varchar(50),@Password nvarchar(50))
AS
begin
Declare @UserId int
	Select @UserId=(Select u.Id From Users u where u.Password=@Password AND u.UserName=@UserName)
	Select u.UserName ,u.Password,u.RegisterDate,ud.FirstName,ud.LastName From Users u,UserDetail ud where u.UserName=@UserName 
	--update Users Set LastLogin=(Select GETDATE()) where Id=@UserId
	return 
end
GO
USE [master]
GO
ALTER DATABASE [MailServer] SET  READ_WRITE 
GO
