use[MailServer]
GO
if Exists(Select * from sys.objects where Type = 'P' and name = 'UpdateUser')
Drop Procedure UpdateUser
Go

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
Update UserDetails set FirstName=@FirstName,LastName=@LastName where Id=@UserId
return
end
GO


