#!/bin/bash
clear

if [ ! $# -eq 1 ] ; then 
	echo " You should provide a secret name. For example, if peekapoo is the secret name:"
        echo " bash copy179.sh peekapoo"
	exit 
fi 

if [ ! -d $HOME/IT179 ] ; then 
	echo " You haven't done your work or put your work in a wrong directory."
	exit 
fi 
chmod -R 700 $HOME/IT179

secret=$HOME/Public/IT179myWork/$1

if [ ! -d $HOME/Public ] ; then
	echo "You didn't create $HOME/Public before"
	mkdir $HOME/Public	
fi
chmod 755 $HOME/Public

if [ ! -d $HOME/Public/IT179myWork ] ; then
	echo "You didn't create $HOME/Public/myIT179myWork before"
	mkdir $HOME/Public/IT179myWork 	
fi
chmod 711 $HOME/Public/IT179myWork 

if [ ! -d $secret ] ; then
	mkdir $secret	 
fi
chmod 777 $secret

cp -r $HOME/IT179/* $secret
chmod -R 777 $secret
echo "Done!!"
