'''
Created on Mar 1, 2016

@author: Anthony

Parsing query string data and returning a 
file path from the result
'''

IIDGroup=None
MidSector=None
MidLabel=None
MidInstitution=None
IIDdt=None
IID_tm=None
YYYY=None
MM=None
DD=None


def pathFinder(path, gDcm):
    
    finalPath = ""
    
    if gDcm == False:
        finalPath = "Sorry, have not yet written for non DICOM"
    else:
        initialPath = path
        fields = startparse(initialPath)
        finalPath = fileSystemLoader(fields)
        
    return finalPath

def startparse(row):
    a = row.split()
    return a;
    
def fileSystemLoader(qLoad):
    global IIDGroup, MidSector, MidLabel, MidInstitution, IIDdt, IID_tm, YYYY, MM, DD
    t = 0
    cmplt = False
    fsLoaderCount = 0
    IIDGroupCount = 0
    MisErr = False
    ValidMidsector = True
    while cmplt == False:
        #print "t= " + qLoad[t],
        count = qLoad[t].count(".")  #finding IIDgroup first for reference
        #print "count= " + str(count)
        #if t >= 13:
        #    break
        
        if count > 5 and t > 2 and t < (len(qLoad) - 2):
            IIDGroup = qLoad[t]
            IIDGroupCount = t
            #print"IIDGroup is: " + IIDGroup
            
        if (t == len(qLoad) - 2):
            if qLoad[t].find("invalid") != -1:
                ValidMidsector = False
            else:
                ValidMidsector = True
                MidSector = qLoad[t]
               
                
        if t == len(qLoad) - 3:
            MidLabel = qLoad[t]
            
        if t == len(qLoad) - 4:
            MidInstitution = qLoad[t]
            
        if IIDGroup != None and t == IIDGroupCount - 4:
            IIDdt = qLoad[t]
            IIDdt = IIDdt.replace("-", ".")
            c = IIDdt.split(".")
            YYYY = c[0]
            MM = c[1]
            DD = c[2]
            
        if IIDdt != None and t == IIDGroupCount - 3:
            IID_tm = qLoad[t]
            IID_tm = IID_tm.replace(":", ".")
            cmplt = True
            #print "IID_tm: " + str(IID_tm)
             
        if t < len(qLoad) - 1:
            t+=1
        else:
            t = 1
        
        fsLoaderCount+=1    
        if fsLoaderCount >= 30:
            MisErr = True
            break     
        
    dcmPath = dicomPathBuild(ValidMidsector, MisErr)
    return dcmPath
            
def dicomPathBuild(ValidMidsector, MisErr):
    m = None
    j = None 
    
    if MisErr == True:
        return "The File System Loader does not recognize the text you copied"
    
    elif ValidMidsector == True:
        m = "/MOUNT/" + MidLabel + "/" + MidInstitution + "/" + str(YYYY) + str(MM) + "/" + str(DD) + "/" + MidSector + "/" + IIDGroup + "/" + str(IID_tm) + "--" + str(IIDdt) + "*"            
        return m
    else:
        j = "/MOUNT/" + MidLabel + "/" + MidInstitution + "/" + str(YYYY) + str(MM) + "/" + str(DD) + "/" + IIDGroup + "/" + str(IID_tm) + "--" + str(IIDdt) + "*"
        return j           
                    
                            
            
        
