USE [master]
GO
/****** Object:  Database [MailServer]    Script Date: 5/27/2021 7:55:50 PM ******/
CREATE DATABASE [MailServer]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'MailServer', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\MailServer.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'MailServer_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\MailServer_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
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
/****** Object:  Table [dbo].[UserRoles]    Script Date: 5/27/2021 7:55:50 PM ******/
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
/****** Object:  Table [dbo].[Users]    Script Date: 5/27/2021 7:55:50 PM ******/
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
/****** Object:  Table [dbo].[UserDetail]    Script Date: 5/27/2021 7:55:50 PM ******/
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
/****** Object:  UserDefinedFunction [dbo].[logins]    Script Date: 5/27/2021 7:55:50 PM ******/
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
/****** Object:  Table [dbo].[DeletedMail]    Script Date: 5/27/2021 7:55:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DeletedMail](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[DeletedDate] [datetime] NULL,
	[RecipientId] [int] NULL,
	[SenderId] [int] NULL,
	[Subject] [varchar](50) NULL,
	[Body] [varchar](max) NULL,
	[Attachment] [varbinary](max) NULL,
	[AttachmentDetail] [varchar](max) NULL,
 CONSTRAINT [PK_DeletedMail] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DraftMail]    Script Date: 5/27/2021 7:55:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DraftMail](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[CreateDate] [datetime] NULL,
	[SenderId] [int] NULL,
	[RecipientId] [int] NULL,
	[Subject] [varchar](50) NULL,
	[Body] [varchar](max) NULL,
	[Attachment] [varbinary](max) NULL,
	[AttachmentDetail] [varchar](max) NULL,
 CONSTRAINT [PK_DraftMail] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Logs]    Script Date: 5/27/2021 7:55:50 PM ******/
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
/****** Object:  Table [dbo].[SentMail]    Script Date: 5/27/2021 7:55:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SentMail](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[CreateDate] [datetime] NULL,
	[SenderId] [int] NOT NULL,
	[RecipientId] [int] NOT NULL,
	[Subject] [varchar](50) NOT NULL,
	[Body] [varchar](max) NOT NULL,
	[Attachment] [varbinary](max) NULL,
	[AttachmentDetail] [varchar](max) NULL,
 CONSTRAINT [PK_SentMail] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Logs] ON 

INSERT [dbo].[Logs] ([Id], [Date], [Type], [ExceptionMessage]) VALUES (1, NULL, N'Deneme', N'başarılı')
SET IDENTITY_INSERT [dbo].[Logs] OFF
GO
SET IDENTITY_INSERT [dbo].[UserDetail] ON 

INSERT [dbo].[UserDetail] ([Id], [UserId], [FirstName], [LastName]) VALUES (4, 1, N'Veysel', N'Veysel')
INSERT [dbo].[UserDetail] ([Id], [UserId], [FirstName], [LastName]) VALUES (5, 1, N'Veysel', N'Veysel')
INSERT [dbo].[UserDetail] ([Id], [UserId], [FirstName], [LastName]) VALUES (6, 2, N'batu', N'batu')
INSERT [dbo].[UserDetail] ([Id], [UserId], [FirstName], [LastName]) VALUES (8, 4, N'batu', N'batu')
SET IDENTITY_INSERT [dbo].[UserDetail] OFF
GO
INSERT [dbo].[UserRoles] ([Id], [Role]) VALUES (1, N'Admin')
INSERT [dbo].[UserRoles] ([Id], [Role]) VALUES (2, N'User')
GO
SET IDENTITY_INSERT [dbo].[Users] ON 

INSERT [dbo].[Users] ([Id], [UserName], [Password], [RoleId], [RegisterDate], [LastLoginDate]) VALUES (1, N'Veysel', N'$2a$10$1.EDZI9gD6xUzOc0BjuZXOYAxHgbzXJB5oVDt1Kb3aVhf/rqMuxbq', 1, CAST(N'2021-05-27' AS Date), CAST(N'2021-05-27T19:03:00' AS SmallDateTime))
INSERT [dbo].[Users] ([Id], [UserName], [Password], [RoleId], [RegisterDate], [LastLoginDate]) VALUES (2, N'batu', N'$2a$10$BndItxg752PHc6J1xfSoE.2MdTq1OcGiRRof7X5KiRwYmUNDcIDTe', 1, CAST(N'2021-05-27' AS Date), CAST(N'2021-05-27T19:03:00' AS SmallDateTime))
INSERT [dbo].[Users] ([Id], [UserName], [Password], [RoleId], [RegisterDate], [LastLoginDate]) VALUES (4, N'Veysel2', N'$2a$10$RRNcL25UTRMor0CLaPmPkeA9Zu7p1H9O4hm2mSGI21D4PiUQfNfVi', 1, CAST(N'2021-05-27' AS Date), CAST(N'2021-05-27T19:48:00' AS SmallDateTime))
SET IDENTITY_INSERT [dbo].[Users] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ_Users_UserName]    Script Date: 5/27/2021 7:55:50 PM ******/
ALTER TABLE [dbo].[Users] ADD  CONSTRAINT [UQ_Users_UserName] UNIQUE NONCLUSTERED 
(
	[UserName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Users] ADD  CONSTRAINT [DF_Users_RegisterDate]  DEFAULT (getdate()) FOR [RegisterDate]
GO
ALTER TABLE [dbo].[DeletedMail]  WITH CHECK ADD  CONSTRAINT [FK_DeletedMail_Users_FromId] FOREIGN KEY([RecipientId])
REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[DeletedMail] CHECK CONSTRAINT [FK_DeletedMail_Users_FromId]
GO
ALTER TABLE [dbo].[DeletedMail]  WITH CHECK ADD  CONSTRAINT [FK_DeletedMail_Users_SendId] FOREIGN KEY([SenderId])
REFERENCES [dbo].[Users] ([Id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[DeletedMail] CHECK CONSTRAINT [FK_DeletedMail_Users_SendId]
GO
ALTER TABLE [dbo].[DraftMail]  WITH CHECK ADD  CONSTRAINT [FK_DraftMail_Users_FromId] FOREIGN KEY([RecipientId])
REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[DraftMail] CHECK CONSTRAINT [FK_DraftMail_Users_FromId]
GO
ALTER TABLE [dbo].[DraftMail]  WITH CHECK ADD  CONSTRAINT [FK_DraftMail_Users_SendId] FOREIGN KEY([SenderId])
REFERENCES [dbo].[Users] ([Id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[DraftMail] CHECK CONSTRAINT [FK_DraftMail_Users_SendId]
GO
ALTER TABLE [dbo].[SentMail]  WITH CHECK ADD  CONSTRAINT [FK_SentMail_Users_FromId] FOREIGN KEY([SenderId])
REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[SentMail] CHECK CONSTRAINT [FK_SentMail_Users_FromId]
GO
ALTER TABLE [dbo].[SentMail]  WITH CHECK ADD  CONSTRAINT [FK_SentMail_Users_SendId] FOREIGN KEY([RecipientId])
REFERENCES [dbo].[Users] ([Id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SentMail] CHECK CONSTRAINT [FK_SentMail_Users_SendId]
GO
/****** Object:  StoredProcedure [dbo].[AddUser]    Script Date: 5/27/2021 7:55:50 PM ******/
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
/****** Object:  StoredProcedure [dbo].[DeleteUser]    Script Date: 5/27/2021 7:55:50 PM ******/
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
/****** Object:  StoredProcedure [dbo].[UpdateUser]    Script Date: 5/27/2021 7:55:50 PM ******/
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
/****** Object:  StoredProcedure [dbo].[UserLogin]    Script Date: 5/27/2021 7:55:50 PM ******/
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
