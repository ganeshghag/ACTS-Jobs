@echo off
set MYCLASSPATH="./jars/*"
@echo on
java -cp %MYCLASSPATH% com.icon.products.acts.jobs.OCRRequestHandler

