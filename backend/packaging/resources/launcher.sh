#!/usr/bin/env bash

config_dir=~/.perobot/config/

java -Dspring.config.additional-location="${config_dir}" @options/launch_options @options/mem_options

exit $?