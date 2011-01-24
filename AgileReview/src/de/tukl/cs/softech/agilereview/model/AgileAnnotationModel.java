package de.tukl.cs.softech.agilereview.model;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * This class is used to store annotations for a given text editor
 */
public class AgileAnnotationModel {

	/**
	 * The texteditor's annotation model
	 */
	private IAnnotationModel annotationModel;
	/**
	 * The annotations added by AgileReview to the editor's annotation model 
	 */
	private HashMap<Position, Annotation> annotationMap = new HashMap<Position, Annotation>();
	
	/**
	 * @param editor The text editor in which the annotations will be displayed
	 */
	public AgileAnnotationModel(IEditorPart editor) {
		IEditorInput input = editor.getEditorInput();
		this.annotationModel = ((ITextEditor)editor).getDocumentProvider().getAnnotationModel(input);
	}
	
	/**
	 * Adds a new annotation at a given position p.
	 * @param p The position to add the annotation on.
	 */
	public void addAnnotation(Position p) {
		
		Annotation annotation = new Annotation("AgileReview.comment.annotation", true, "AgileReview Annotation");
		this.annotationModel.addAnnotation(annotation, p);
		
	}
	
	/**
	 * Deletes an existing annotation at a given position p.
	 * @param p The position where the annotation will be deleted.
	 */
	public void deleteAnnotation(Position p) {
		
		// Delete from local savings
		Annotation annotation = this.annotationMap.get(p);
		Position position = this.annotationModel.getPosition(annotation);
		// Delete from AnnotationModel
		this.annotationMap.remove(position);
		annotation.markDeleted(true);
		position.delete();
		this.annotationModel.removeAnnotation(annotation);
	}
	
	/**
	 * Removes all annotations from the editor's annotation model.
	 */
	public void deleteAllAnnoations() {
		if (!this.annotationMap.isEmpty()) {
			HashSet<Position> delPos = new HashSet<Position>();
			delPos.addAll(annotationMap.keySet());
			for (Position position : delPos) {
				deleteAnnotation(position);
			}
		}
	}
	
}
