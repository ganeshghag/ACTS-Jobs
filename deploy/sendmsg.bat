@echo off
set MYCLASSPATH="./jars/*"
@echo on
java -cp %MYCLASSPATH% com.icon.products.acts.jobs.utils.JmsMsgSender d:\\temp\\CaseFolders\\Case_101 form14

