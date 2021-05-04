#!/usr/bin/env sh
# mvn deploy:deploy-file -DgroupId=org.netbeans.netbeans82 -DartifactId=org-netbeans-modules-cnd-apt -Dversion=8.2 -Durl=file:./nb82/ -DrepositoryId=nb82 -DupdateReleaseInfo=true -Dfile=org-netbeans-modules-cnd-apt.jar 
mvn deploy:deploy-file -DgroupId=org.netbeans.netbeans82 -DartifactId=org-netbeans-modules-cnd-utils -Dversion=8.2 -Durl=file:./nb82/ -DrepositoryId=nb82 -DupdateReleaseInfo=true -Dfile=org-netbeans-modules-cnd-utils.jar
mvn deploy:deploy-file -DgroupId=org.netbeans.netbeans82 -DartifactId=org-openide-util-lookup -Dversion=8.2 -Durl=file:./nb82/ -DrepositoryId=nb82 -DupdateReleaseInfo=true -Dfile=org-openide-util-lookup.jar
mvn deploy:deploy-file -DgroupId=org.netbeans.netbeans82 -DartifactId=org-openide-util -Dversion=8.2 -Durl=file:./nb82/ -DrepositoryId=nb82 -DupdateReleaseInfo=true -Dfile=org-openide-util.jar
