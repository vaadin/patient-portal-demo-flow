#!/bin/sh

function usage
{
    echo "usage: $0 -branch branch_name -token api_token [-message commit_message] [-cfile certificate_file]"
}

while [ "$1" != "" ]; do
    case $1 in
         -branch )       shift
                         branch=$1
                         ;;
         -cfile )        shift
                         certificate=$1
                         ;;
         -message )      shift
                         message=$1
                         ;;
         -token )        shift
                         token=$1
                         ;;
         * )             usage
                         exit 1
    esac
    shift
done

if [ -z $branch ]; then
   usage
   exit 1
fi

if [ -z $token ]; then
   usage
   exit 1
fi


if [ -z $certificate ]; then
   cert_option=" -k "
else

   cert_option=" --cacert $certificate "
fi

if [ -z $message ]; then
body='{
"request": {
"branch": '"\"$branch\"}}"
else
body='{
"request": {
"message": '"\"$message\",
\"branch\": \"$branch\"}}"
fi

curl $cert_option -X POST \
   -H "Content-Type: application/json" \
   -H "Accept: application/json" \
   -H "Travis-API-Version: 3" \
   -H "Authorization: token $token" \
   -d "$body" \
   https://api.travis-ci.com/repo/vaadin%2Fflow-hello-world/requests

