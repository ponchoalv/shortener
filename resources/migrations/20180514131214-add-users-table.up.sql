CREATE TABLE ShortenerGBSJ.dbo.shorten
(
    short varchar(20) PRIMARY KEY NOT NULL,
    url varchar(150) NOT NULL,
    fecha_cargado datetime DEFAULT sysdatetime() NOT NULL
)
CREATE UNIQUE INDEX shorten_short_uindex ON ShortenerGBSJ.dbo.shorten (short)
