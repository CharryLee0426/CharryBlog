# Lichen_Blog

#### Description
A Blog system that contains an administration page. 
You can uploud blogs online but not during ssh client.

#### Software Architecture
A standard SpringBoot project.

#### Installation

1. git clone
2. open the project and solve dependency problem by maven.
3. edit `/src/application.yml`, 
you must change MySQL's username and password to your own.
4. Create database name 'blog' in your MySQL.
5. Build and Run the Project at BlogApplication. 
You can visit it through `localhost:8080/`
(if you don't change server port.)

#### Instructions

1. Run on port 8080 
(if you don't change default server port in `application.yml` )
2. Create a record at the table t_user as your admin
3. Alter the attribute content's data-type 
at t_blog and t_comment to 'longtext', or you can only
store 255 chars in a record.
4. Only can update the picture's url to the system, support markdown syntax.
I recommand the 'superbed.cn' [http://www.superbed.cn](http://www.superbed.cn) 
as your Picbed.

#### Contribution

1.  Fork the repository
2.  Create branch
3.  Commit your code
4.  Create Pull Request
