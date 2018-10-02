-- :name save-shorten! :! :n
-- :doc creates a new shorten record
INSERT INTO ShortenerGBSJ.dbo.shorten
(short, url)
VALUES (:short, :url)

-- :name get-url :? :1
-- :doc retrieves a url record given the short url
SELECT url FROM ShortenerGBSJ.dbo.shorten
WHERE short = :short
