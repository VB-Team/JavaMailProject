use[MailServer]
GO
if Exists(Select * from sys.objects where Type = 'P' and name = 'AddUser')
Drop Procedure AddUser
Go


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
Select @RoleId=(Select ur.Id from UserRoles ur where ur.Role=@Role)
INSERT INTO Users (UserName,Password,RoleId,LastLoginDate,RegisterDate)Values(@UserName,@Password,@RoleId,@LastLoginDate,@LastLoginDate)
Select @UserId=(Select u.Id From Users u where Id=SCOPE_IDENTITY())
Insert Into UserDetails(FirstName,LastName,UserId) Values(@FirstName,@LastName,@UserId)
return
end
GO


