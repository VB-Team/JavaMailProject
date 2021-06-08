use[MailServer]
GO
if Exists(Select * from sys.objects where Type = 'P' and name = 'DeleteUser')
Drop Procedure DeleteUser
Go


create procedure [dbo].[DeleteUser] (@UserId int)
as
begin
Delete From Users where Id=@UserId
Delete From UserDetails where Id=@UserId
return
end
GO


