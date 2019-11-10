USE [master]
GO

/* For security reasons the login is created disabled and with a random password. */
/****** Object:  Login [quinnadmin]    Script Date: 10/11/2019 15:49:40 ******/
CREATE LOGIN [quinnadmin] WITH PASSWORD=N'bLY41+QU4LwLQp0tgcyA0cC9lJQUj+MzPLJ+AzvGsh4=', DEFAULT_DATABASE=[quinn_inc], DEFAULT_LANGUAGE=[us_english], CHECK_EXPIRATION=ON, CHECK_POLICY=ON
GO

ALTER LOGIN [quinnadmin] DISABLE
GO

ALTER SERVER ROLE [sysadmin] ADD MEMBER [quinnadmin]
GO

ALTER SERVER ROLE [securityadmin] ADD MEMBER [quinnadmin]
GO

ALTER SERVER ROLE [serveradmin] ADD MEMBER [quinnadmin]
GO

ALTER SERVER ROLE [diskadmin] ADD MEMBER [quinnadmin]
GO

