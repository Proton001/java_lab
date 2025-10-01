@echo off
chcp 866
set ROOT=%~dp0
java -Dfile.encoding=Cp866 -Dsun.stdout.encoding=Cp866 -Dsun.stderr.encoding=Cp866 -Dsun.io.unicode.encoding=UnicodeLittle -cp "%ROOT%out" com.example.dungeon.Main -Dfile.encoding=Cp866 -Dconsole.encoding=Cp866
