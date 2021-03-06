USE [master]
GO
/****** Object:  Database [MailServer]    Script Date: 5/31/2021 1:30:09 AM ******/
CREATE DATABASE [MailServer]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'MailServer', FILENAME = N'/var/opt/mssql/data/MailServer.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'MailServer_log', FILENAME = N'/var/opt/mssql/data/MailServer_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
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
ALTER DATABASE [MailServer] SET QUERY_STORE = OFF
GO
USE [MailServer]
GO
/****** Object:  Table [dbo].[Attachments]    Script Date: 5/31/2021 1:30:09 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Attachments](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[MailId] [int] NOT NULL,
	[AttachmentName] [nvarchar](50) NULL,
	[AttachmentType] [nvarchar](10) NULL,
	[AttachmentSize] [int] NULL,
	[AttachmentContent] [varbinary](max) NULL,
 CONSTRAINT [PK_Attachments] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Headers]    Script Date: 5/31/2021 1:30:09 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Headers](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[MailId] [int] NOT NULL,
	[RecipientId] [int] NULL,
	[SenderId] [int] NULL,
	[Type] [varchar](10) NULL,
	[State] [bit] NULL,
 CONSTRAINT [PK_Header] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Logs]    Script Date: 5/31/2021 1:30:09 AM ******/
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
/****** Object:  Table [dbo].[Mails]    Script Date: 5/31/2021 1:30:09 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Mails](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Subject] [nvarchar](108) NOT NULL,
	[Body] [nvarchar](max) NOT NULL,
	[AttachmentState] [bit] NULL,
	[CreateDate] [smalldatetime] NULL,
 CONSTRAINT [PK_Mails_1] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserDetails]    Script Date: 5/31/2021 1:30:09 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserDetails](
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
/****** Object:  Table [dbo].[UserRoles]    Script Date: 5/31/2021 1:30:09 AM ******/
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
/****** Object:  Table [dbo].[Users]    Script Date: 5/31/2021 1:30:09 AM ******/
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
SET IDENTITY_INSERT [dbo].[Attachments] ON 

INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (10, 11, N'Deneme', N'asdasdasd', 123, NULL)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (13, 13, N'Deneme', N'asdasdasd', 123, NULL)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (16, 15, N'Attachment0', N'Type0', 0, 0x00)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (17, 15, N'Attachment0', N'Type0', 0, 0x00)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (18, 15, N'Attachment0', N'Type0', 0, 0x00)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (19, 15, N'Attachment0', N'Type0', 0, 0x00)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (20, 15, N'Attachment0', N'Type0', 0, 0x00)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (21, 15, N'Attachment0', N'Type0', 0, 0x00)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (22, 15, N'Attachment0', N'Type0', 0, 0x00)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (23, 15, N'Attachment0', N'Type0', 0, 0x00)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (24, 15, N'Attachment0', N'Type0', 0, 0x00)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (25, 15, N'Attachment0', N'Type0', 0, 0x00)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (26, 16, N'Attachment1', N'Type1', 1, 0x01)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (27, 16, N'Attachment1', N'Type1', 1, 0x01)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (28, 16, N'Attachment1', N'Type1', 1, 0x01)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (29, 16, N'Attachment1', N'Type1', 1, 0x01)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (30, 16, N'Attachment1', N'Type1', 1, 0x01)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (31, 16, N'Attachment1', N'Type1', 1, 0x01)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (32, 16, N'Attachment1', N'Type1', 1, 0x01)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (33, 16, N'Attachment1', N'Type1', 1, 0x01)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (34, 16, N'Attachment1', N'Type1', 1, 0x01)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (35, 16, N'Attachment1', N'Type1', 1, 0x01)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (36, 17, N'Attachment2', N'Type2', 2, 0x02)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (37, 17, N'Attachment2', N'Type2', 2, 0x02)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (38, 17, N'Attachment2', N'Type2', 2, 0x02)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (39, 17, N'Attachment2', N'Type2', 2, 0x02)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (40, 17, N'Attachment2', N'Type2', 2, 0x02)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (41, 17, N'Attachment2', N'Type2', 2, 0x02)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (42, 17, N'Attachment2', N'Type2', 2, 0x02)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (43, 17, N'Attachment2', N'Type2', 2, 0x02)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (44, 17, N'Attachment2', N'Type2', 2, 0x02)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (45, 17, N'Attachment2', N'Type2', 2, 0x02)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (46, 18, N'Attachment3', N'Type3', 3, 0x03)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (47, 18, N'Attachment3', N'Type3', 3, 0x03)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (48, 18, N'Attachment3', N'Type3', 3, 0x03)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (49, 18, N'Attachment3', N'Type3', 3, 0x03)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (50, 18, N'Attachment3', N'Type3', 3, 0x03)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (51, 18, N'Attachment3', N'Type3', 3, 0x03)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (52, 18, N'Attachment3', N'Type3', 3, 0x03)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (53, 18, N'Attachment3', N'Type3', 3, 0x03)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (54, 18, N'Attachment3', N'Type3', 3, 0x03)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (55, 18, N'Attachment3', N'Type3', 3, 0x03)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (56, 19, N'Attachment4', N'Type4', 4, 0x04)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (57, 19, N'Attachment4', N'Type4', 4, 0x04)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (58, 19, N'Attachment4', N'Type4', 4, 0x04)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (59, 19, N'Attachment4', N'Type4', 4, 0x04)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (60, 19, N'Attachment4', N'Type4', 4, 0x04)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (61, 19, N'Attachment4', N'Type4', 4, 0x04)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (62, 19, N'Attachment4', N'Type4', 4, 0x04)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (63, 19, N'Attachment4', N'Type4', 4, 0x04)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (64, 19, N'Attachment4', N'Type4', 4, 0x04)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (65, 19, N'Attachment4', N'Type4', 4, 0x04)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (76, 21, N'Attachment6', N'Type6', 6, 0x06)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (77, 21, N'Attachment6', N'Type6', 6, 0x06)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (78, 21, N'Attachment6', N'Type6', 6, 0x06)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (79, 21, N'Attachment6', N'Type6', 6, 0x06)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (80, 21, N'Attachment6', N'Type6', 6, 0x06)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (81, 21, N'Attachment6', N'Type6', 6, 0x06)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (82, 21, N'Attachment6', N'Type6', 6, 0x06)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (83, 21, N'Attachment6', N'Type6', 6, 0x06)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (84, 21, N'Attachment6', N'Type6', 6, 0x06)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (85, 21, N'Attachment6', N'Type6', 6, 0x06)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (86, 22, N'Attachment7', N'Type7', 7, 0x07)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (87, 22, N'Attachment7', N'Type7', 7, 0x07)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (88, 22, N'Attachment7', N'Type7', 7, 0x07)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (89, 22, N'Attachment7', N'Type7', 7, 0x07)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (90, 22, N'Attachment7', N'Type7', 7, 0x07)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (91, 22, N'Attachment7', N'Type7', 7, 0x07)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (92, 22, N'Attachment7', N'Type7', 7, 0x07)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (93, 22, N'Attachment7', N'Type7', 7, 0x07)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (94, 22, N'Attachment7', N'Type7', 7, 0x07)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (95, 22, N'Attachment7', N'Type7', 7, 0x07)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (96, 23, N'Attachment8', N'Type8', 8, 0x08)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (97, 23, N'Attachment8', N'Type8', 8, 0x08)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (98, 23, N'Attachment8', N'Type8', 8, 0x08)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (99, 23, N'Attachment8', N'Type8', 8, 0x08)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (100, 23, N'Attachment8', N'Type8', 8, 0x08)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (101, 23, N'Attachment8', N'Type8', 8, 0x08)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (102, 23, N'Attachment8', N'Type8', 8, 0x08)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (103, 23, N'Attachment8', N'Type8', 8, 0x08)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (104, 23, N'Attachment8', N'Type8', 8, 0x08)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (105, 23, N'Attachment8', N'Type8', 8, 0x08)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (106, 24, N'Attachment9', N'Type9', 9, 0x09)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (107, 24, N'Attachment9', N'Type9', 9, 0x09)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (108, 24, N'Attachment9', N'Type9', 9, 0x09)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (109, 24, N'Attachment9', N'Type9', 9, 0x09)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (110, 24, N'Attachment9', N'Type9', 9, 0x09)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (111, 24, N'Attachment9', N'Type9', 9, 0x09)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (112, 24, N'Attachment9', N'Type9', 9, 0x09)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (113, 24, N'Attachment9', N'Type9', 9, 0x09)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (114, 24, N'Attachment9', N'Type9', 9, 0x09)
INSERT [dbo].[Attachments] ([Id], [MailId], [AttachmentName], [AttachmentType], [AttachmentSize], [AttachmentContent]) VALUES (115, 24, N'Attachment9', N'Type9', 9, 0x09)
SET IDENTITY_INSERT [dbo].[Attachments] OFF
GO
SET IDENTITY_INSERT [dbo].[Headers] ON 

INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (10, 11, 1, 2, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (13, 13, 1, 2, N'Draft', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (16, 15, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (17, 15, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (18, 15, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (19, 15, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (20, 15, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (21, 15, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (22, 15, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (23, 15, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (24, 15, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (25, 15, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (26, 16, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (27, 16, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (28, 16, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (29, 16, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (30, 16, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (31, 16, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (32, 16, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (33, 16, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (34, 16, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (35, 16, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (36, 17, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (37, 17, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (38, 17, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (39, 17, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (40, 17, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (41, 17, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (42, 17, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (43, 17, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (44, 17, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (45, 17, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (46, 18, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (47, 18, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (48, 18, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (49, 18, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (50, 18, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (51, 18, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (52, 18, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (53, 18, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (54, 18, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (55, 18, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (56, 19, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (57, 19, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (58, 19, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (59, 19, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (60, 19, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (61, 19, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (62, 19, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (63, 19, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (64, 19, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (65, 19, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (76, 21, 2, 1, N'Draft', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (77, 21, 2, 1, N'Draft', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (78, 21, 2, 1, N'Draft', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (79, 21, 2, 1, N'Draft', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (80, 21, 2, 1, N'Draft', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (81, 21, 2, 1, N'Draft', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (82, 21, 2, 1, N'Draft', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (83, 21, 2, 1, N'Draft', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (84, 21, 2, 1, N'Draft', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (85, 21, 2, 1, N'Draft', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (86, 22, 1, 2, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (87, 22, 1, 2, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (88, 22, 1, 2, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (89, 22, 1, 2, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (90, 22, 1, 2, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (91, 22, 1, 2, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (92, 22, 1, 2, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (93, 22, 1, 2, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (94, 22, 1, 2, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (95, 22, 1, 2, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (96, 23, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (97, 23, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (98, 23, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (99, 23, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (100, 23, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (101, 23, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (102, 23, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (103, 23, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (104, 23, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (105, 23, 2, 1, N'Normal', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (106, 24, 2, 1, N'Deleted', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (107, 24, 2, 1, N'Deleted', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (108, 24, 2, 1, N'Deleted', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (109, 24, 2, 1, N'Deleted', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (110, 24, 2, 1, N'Deleted', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (111, 24, 2, 1, N'Deleted', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (112, 24, 2, 1, N'Deleted', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (113, 24, 2, 1, N'Deleted', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (114, 24, 2, 1, N'Deleted', 1)
INSERT [dbo].[Headers] ([Id], [MailId], [RecipientId], [SenderId], [Type], [State]) VALUES (115, 24, 2, 1, N'Deleted', 1)
SET IDENTITY_INSERT [dbo].[Headers] OFF
GO
SET IDENTITY_INSERT [dbo].[Logs] ON 

INSERT [dbo].[Logs] ([Id], [Date], [Type], [ExceptionMessage]) VALUES (1, NULL, N'Deneme', N'basarili')
SET IDENTITY_INSERT [dbo].[Logs] OFF
GO
SET IDENTITY_INSERT [dbo].[Mails] ON 

INSERT [dbo].[Mails] ([Id], [Subject], [Body], [AttachmentState], [CreateDate]) VALUES (11, N'asdasd', N'asdasdasd', 1, CAST(N'2021-05-30T00:00:00' AS SmallDateTime))
INSERT [dbo].[Mails] ([Id], [Subject], [Body], [AttachmentState], [CreateDate]) VALUES (13, N'asdasd', N'Mail Add Deneme', 1, CAST(N'2021-05-30T00:00:00' AS SmallDateTime))
INSERT [dbo].[Mails] ([Id], [Subject], [Body], [AttachmentState], [CreateDate]) VALUES (15, N'Deneme Subject0', N'Deneme Body0', 1, CAST(N'2021-05-30T23:03:00' AS SmallDateTime))
INSERT [dbo].[Mails] ([Id], [Subject], [Body], [AttachmentState], [CreateDate]) VALUES (16, N'Deneme Subject1', N'Deneme Body1', 1, CAST(N'2021-05-30T23:03:00' AS SmallDateTime))
INSERT [dbo].[Mails] ([Id], [Subject], [Body], [AttachmentState], [CreateDate]) VALUES (17, N'Deneme Subject2', N'Deneme Body2', 1, CAST(N'2021-05-30T23:03:00' AS SmallDateTime))
INSERT [dbo].[Mails] ([Id], [Subject], [Body], [AttachmentState], [CreateDate]) VALUES (18, N'Deneme Subject3', N'Deneme Body3', 1, CAST(N'2021-05-30T23:03:00' AS SmallDateTime))
INSERT [dbo].[Mails] ([Id], [Subject], [Body], [AttachmentState], [CreateDate]) VALUES (19, N'Deneme Subject4', N'Deneme Body4', 1, CAST(N'2021-05-30T23:03:00' AS SmallDateTime))
INSERT [dbo].[Mails] ([Id], [Subject], [Body], [AttachmentState], [CreateDate]) VALUES (21, N'Deneme Subject6', N'Deneme Body6', 1, CAST(N'2021-05-30T23:03:00' AS SmallDateTime))
INSERT [dbo].[Mails] ([Id], [Subject], [Body], [AttachmentState], [CreateDate]) VALUES (22, N'Deneme Subject7', N'Deneme Body7', 1, CAST(N'2021-05-30T23:03:00' AS SmallDateTime))
INSERT [dbo].[Mails] ([Id], [Subject], [Body], [AttachmentState], [CreateDate]) VALUES (23, N'Deneme Subject8', N'Deneme Body8', 1, CAST(N'2021-05-30T23:03:00' AS SmallDateTime))
INSERT [dbo].[Mails] ([Id], [Subject], [Body], [AttachmentState], [CreateDate]) VALUES (24, N'Deneme Subject9', N'Deneme Body9', 1, CAST(N'2021-05-30T23:03:00' AS SmallDateTime))
SET IDENTITY_INSERT [dbo].[Mails] OFF
GO
SET IDENTITY_INSERT [dbo].[UserDetails] ON 

INSERT [dbo].[UserDetails] ([Id], [UserId], [FirstName], [LastName]) VALUES (1, 1, N'Veysel', N'Karaca')
INSERT [dbo].[UserDetails] ([Id], [UserId], [FirstName], [LastName]) VALUES (2, 2, N'Batu', N'San')
SET IDENTITY_INSERT [dbo].[UserDetails] OFF
GO
INSERT [dbo].[UserRoles] ([Id], [Role]) VALUES (1, N'Admin')
INSERT [dbo].[UserRoles] ([Id], [Role]) VALUES (2, N'User')
GO
SET IDENTITY_INSERT [dbo].[Users] ON 

INSERT [dbo].[Users] ([Id], [UserName], [Password], [RoleId], [RegisterDate], [LastLoginDate]) VALUES (1, N'Veysel', N'$2a$10$NSHphMXvV4GTd9G.dp8NGuCrHXTWlW2tq1bcGxLM/BpKuOFq/aFau', 1, CAST(N'2021-05-27' AS Date), CAST(N'2021-05-31T01:25:00' AS SmallDateTime))
INSERT [dbo].[Users] ([Id], [UserName], [Password], [RoleId], [RegisterDate], [LastLoginDate]) VALUES (2, N'Batu', N'$2a$10$MJafOM.c0EAnE8pcOp2eOutNCz7IELtac0EYJxdDFZl9ZXgBvP1wa', 1, CAST(N'2021-05-27' AS Date), CAST(N'2021-05-28T21:29:00' AS SmallDateTime))
SET IDENTITY_INSERT [dbo].[Users] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ_Users_UserName]    Script Date: 5/31/2021 1:30:10 AM ******/
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
ALTER TABLE [dbo].[Attachments]  WITH CHECK ADD  CONSTRAINT [FK_Attachments_Mails] FOREIGN KEY([MailId])
REFERENCES [dbo].[Mails] ([Id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Attachments] CHECK CONSTRAINT [FK_Attachments_Mails]
GO
ALTER TABLE [dbo].[Headers]  WITH CHECK ADD  CONSTRAINT [FK_Header_Users_RecipientId] FOREIGN KEY([RecipientId])
REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Headers] CHECK CONSTRAINT [FK_Header_Users_RecipientId]
GO
ALTER TABLE [dbo].[Headers]  WITH CHECK ADD  CONSTRAINT [FK_Header_Users_SenderId] FOREIGN KEY([SenderId])
REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Headers] CHECK CONSTRAINT [FK_Header_Users_SenderId]
GO
ALTER TABLE [dbo].[Headers]  WITH CHECK ADD  CONSTRAINT [FK_Headers_Mails] FOREIGN KEY([MailId])
REFERENCES [dbo].[Mails] ([Id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Headers] CHECK CONSTRAINT [FK_Headers_Mails]
GO
ALTER TABLE [dbo].[Mails]  WITH CHECK ADD  CONSTRAINT [FK_Mails_Mails] FOREIGN KEY([Id])
REFERENCES [dbo].[Mails] ([Id])
GO
ALTER TABLE [dbo].[Mails] CHECK CONSTRAINT [FK_Mails_Mails]
GO
ALTER TABLE [dbo].[UserDetails]  WITH CHECK ADD  CONSTRAINT [FK_UserDetails_Users] FOREIGN KEY([UserId])
REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[UserDetails] CHECK CONSTRAINT [FK_UserDetails_Users]
GO
/****** Object:  StoredProcedure [dbo].[AddUser]    Script Date: 5/31/2021 1:30:10 AM ******/
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
/****** Object:  StoredProcedure [dbo].[DeleteUser]    Script Date: 5/31/2021 1:30:10 AM ******/
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
/****** Object:  StoredProcedure [dbo].[UpdateUser]    Script Date: 5/31/2021 1:30:10 AM ******/
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
/****** Object:  StoredProcedure [dbo].[UserLogin]    Script Date: 5/31/2021 1:30:10 AM ******/
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
