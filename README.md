# Isha CarPool
Simple mobile friendly web app to request / offer carpool for pre-defined trips for Isha meditators.

# Technologies
This is a single page application (SPA) built using a few frameworks.

## Client

### Framework
After evaluating both AngularJs and ReactJs, I ended up choosing AngularJs because of the following reasons:

1. Compiling and rendering the html using ReactJs needed a javascript compiler in the server side. Since I was pre-committed to Java and found it dangerous to use nashorn to compile javascript in the server side, didn't do that. However, I liked the idea of having nodejs as a middle-tier that gets json from a Java service. I may switch to that in future.
1. AngularJs seemed to involve less of Javascript than ReactJs. Again, was just playing to my strength.

At one point, I was too confused about picking a framework that I thought I would do it without any framework. Somehow, made AngularJs work. So, for the foreseeable future, I will continue with that.

### Bootstrap &amp; Jquery
I guess, these are no-brainers.

## Server
### Language
Chose Java as I am super fluent with it.

### Framework
Was very particular that the framework should be lightweight and not stand in front of what I want to do. So, approached all Spring based one's like Roo, SpringMVC with lot of caution. Also, realized that, for a Single Page Application, these won't fit the bill. Ended up with Sparkjava and have zero regrets.

1. Super simple and lightweight framework. One wonders what exactly it provides. It looked like Java 8's  answer for nodejs + express.
1. Found a way to make it work over Heroku.

## Deployment / Hosting Platform
I started with Google Appengine as I use that at work and have grown to like it for the simplicity it provides in dealing with a key-value store (Datastore). However, after just one day of running a simple app, realized that it is way too co$tly. It looked like Heroku was the only PaaS that was free. Just because of the cost, was even willing to use PostgreSql with Hibernate (Ah! it would have been like going back 10 years!).

Heroku ended up being so awesome. Will definitely use it in future.

## Database
Was looking for a simple key-value store like Datastore and didn't know that Datastore was really a Document database. For some reason, I had thought Document database meant something else. Looking at the MongoDb, mainly Morphia, examples, it was clear that this is exactly what I wanted.

So, choose MongoDb, and Morphia to talk to it. No regrets. Has been hassle free. mLabs provides free Sandbox instances of this in Heroku and that's enough for me.

## Authentication
I had pre-decided that I will not make my users remember yet another username/password combination. So, decided that I will use Signin with Google. Getting it to work was only less painful than getting AngularJs to work. Some of it was my own stupidity that made things a little difficult to debug. Overall, once I made it work, it has been seemless.

## Summary
So, in summary, I built this app, that
* Sparkjava framework to deliver both the static files like js, images, css etc., and the dynamic web app built over Java 8,
* uses closure templates,
* authenticates the user through Sign-in with Google,
* persists information in MongoDb through Morphia,
* renders json blobs over /api/ endpoints, html templated with closure, and static files, all from the same Sparkjava framework (I may eventually move the client app to a separate repo and framework),
* and mainly, creates a single-page application using AngularJs.

It took 3 days to develop the app to its current state where I can create, view and list trips. Long way to go.
