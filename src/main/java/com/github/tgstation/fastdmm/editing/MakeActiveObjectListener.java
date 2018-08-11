package com.github.tgstation.fastdmm.editing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import com.github.tgstation.fastdmm.FastDMM;
import com.github.tgstation.fastdmm.dmmmap.Location;
import com.github.tgstation.fastdmm.objtree.ModifiedType;
import com.github.tgstation.fastdmm.objtree.ObjInstance;
import com.github.tgstation.fastdmm.objtree.ObjectTreeItem;

public class MakeActiveObjectListener implements ActionListener {
	FastDMM editor;
	ObjInstance oInstance;
	
	public MakeActiveObjectListener(FastDMM editor, Location mapLocation, ObjInstance instance) {
		this.editor = editor;
		this.oInstance = instance;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(editor.dmm == null)
			return;
		synchronized(editor) {
			editor.selectedObject = oInstance instanceof ObjectTreeItem ? (ObjectTreeItem)oInstance : ((ModifiedType)oInstance).parent;
			editor.selectedInstance = oInstance;
			List<Object> path = new LinkedList<>();
			ObjectTreeItem curr = editor.selectedObject;
			while(curr != null && (curr.istype("/area") || curr.istype("/mob") || curr.istype("/obj") || curr.istype("/turf"))) {
				path.add(0, curr);
				curr = curr.parent;
			}
			path.add(0, editor.objTree);
			
			editor.objTreeVis.setSelectionPath(new TreePath(path.toArray()));
			editor.instancesVis.setSelectedValue(editor.selectedInstance, true);
		}
	}
}
