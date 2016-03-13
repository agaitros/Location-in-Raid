'''
Created on Mar 9, 2016

@author: Anthony
'''

import sys
import locationinRaidFinder, win32clipboard
from PyQt4 import QtGui, QtCore


class fsLoader(QtGui.QLabel):
    
    def __init__(self):
        super(fsLoader, self).__init__()
        self.setWindowTitle("Your image is in...")
        
        
    def wstartup(self, lcnt, ltext):   
        self.setGeometry(700, 400, lcnt, 100)  
        self.setAlignment(QtCore.Qt.AlignCenter)
        self.setText(ltext)
        self.CpyFs(ltext)    
        self.show()
        
    def CpyFs(self, btext):
        btn = QtGui.QPushButton("Copy this File System", self)
        btn.clicked.connect(lambda: self.ctrlC(btext)) 

    def ctrlC(self, ctext):
        win32clipboard.OpenClipboard()
        win32clipboard.EmptyClipboard()
        win32clipboard.SetClipboardText(ctext)
        win32clipboard.CloseClipboard()
        

def startup():
    
    # A little leg work
    win32clipboard.OpenClipboard()
    lstring= win32clipboard.GetClipboardData()
    win32clipboard.CloseClipboard()
    
    fsPath = locationinRaidFinder.pathFinder(lstring, True)
    fsCnt = len(fsPath) * 6.5
    
    #start window construction
    frame = QtGui.QApplication(sys.argv)
    app = fsLoader()
    app.wstartup(fsCnt, fsPath)
    sys.exit(frame.exec_())


if __name__ == "__main__":
    startup()