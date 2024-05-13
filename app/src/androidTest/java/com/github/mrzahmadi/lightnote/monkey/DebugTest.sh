#!/bin/bash
package_name="com.github.mrzahmadi.lightnote.debug"

adb shell monkey -p "$package_name" -v 500  > monkey_log.txt

exit