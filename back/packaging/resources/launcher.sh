#!/bin/sh

required_version=15

message()
{
  TITLE="Cannot start Perobobbot"
  if [ -n "$(command -v zenity)" ]; then
    zenity --error --title="$TITLE" --text="$1" --no-wrap
  elif [ -n "$(command -v kdialog)" ]; then
    kdialog --error "$1" --title "$TITLE"
  elif [ -n "$(command -v notify-send)" ]; then
    notify-send "ERROR: $TITLE" "$1"
  elif [ -n "$(command -v xmessage)" ]; then
    xmessage -center "ERROR: $TITLE: $1"
  else
    printf "ERROR: %s\n%s\n" "$TITLE" "$1"
  fi
}

extract_version() {
  IFS=. read major minor extra <<<$( $1 -version 2>&1 | awk -F '"' '/version/ {print $2}');
  echo $major
}

check_version() {
  local version;
  version=$( extract_version $1 )
  if [ $version -ge $required_version ]; then
     echo $1
  else
     echo ""
  fi
}

os_version=`which java`;

if [ -z "$JDK" ] & [ -x "$os_version" ]; then
   JDK=$( check_version "$os_version" )
fi

if [ -z "$JDK" ] & [ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ]; then
   JDK=$( check_version "$JAVA_HOME/bin/java" )
fi

if [ -z $JDK ]; then
   message "At least version 15 of Java is required"
   exit 1
fi

"$JAVA_HOME/bin/java" @options/launch_options @options/mem_options

exit $?
