{\rtf1\ansi\ansicpg1252\cocoartf1561\cocoasubrtf600
{\fonttbl\f0\fnil\fcharset0 Menlo-Regular;}
{\colortbl;\red255\green255\blue255;\red70\green70\blue70;\red254\green254\blue251;}
{\*\expandedcolortbl;;\cssrgb\c34510\c34510\c34510;\cssrgb\c99608\c99608\c98824;}
\paperw11900\paperh16840\margl1440\margr1440\vieww10800\viewh8400\viewkind0
\deftab720
\pard\pardeftab720\sl420\partightenfactor0

\f0\fs28 \cf2 \cb3 \expnd0\expndtw0\kerning0
\outl0\strokewidth0 \strokec2 #!/bin/sh\
\
export JAVA_HOME=/usr/local/java\
PATH=/usr/local/java/bin:$\{PATH\}\
\
cd /Users/mxg6920/Connect4/\
\
#---------------------------------#\
# 		Dynamical build           #\
#---------------------------------#\
THE_CLASSPATH=\
for i in `ls ./lib/*.jar`\
do\
  THE_CLASSPATH=$\{THE_CLASSPATH\}:$\{i\}\
done\
\
#---------------------------#\
# run the anti-spam program #\
#---------------------------#\
java -cp ".:$\{THE_CLASSPATH\}"  \\\
  }