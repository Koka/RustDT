/*******************************************************************************
 * Copyright (c) 2015, 2015 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package melnorme.lang.ide.ui.text.completion;

import static melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import melnorme.lang.ide.ui.editor.LangSourceViewer;
import melnorme.lang.ide.ui.templates.LangTemplateProposal;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalSorter;

public class ContentAssistantExt extends ContentAssistant {
	
	protected final IPreferenceStore prefStore;
	
	public ContentAssistantExt(IPreferenceStore prefStore) {
		this.prefStore = assertNotNull(prefStore);
		setSorter(new ContentAssistSorter());
	}
	
	public void configure(IPreferenceStore prefStore, LangSourceViewer langSourceViewer) {
		new ContentAssistPreferenceHandler(this, prefStore, langSourceViewer).configureViewer();
	}
	
	/* -----------------  ----------------- */
	
	public static class ContentAssistSorter implements ICompletionProposalSorter {
		@Override
		public int compare(ICompletionProposal proposalA, ICompletionProposal proposalB) {
			int relevanceA = getRelevance(proposalA);
			int relevanceB = getRelevance(proposalB);
			
			if(relevanceA != relevanceB) {
				return Integer.compare(relevanceA, relevanceB);
			}
			
			String p1SortString = getSortString(proposalA);
			String p2SortString = getSortString(proposalB);
			return p1SortString.compareTo(p2SortString);
		}

		protected int getRelevance(ICompletionProposal proposal) {
			if(proposal instanceof LangCompletionProposal) {
				LangCompletionProposal langProposal = (LangCompletionProposal) proposal;
				return langProposal.getRelevance();
			}
			
			return proposal instanceof LangTemplateProposal ? 100 : 0;
		}
		
		protected String getSortString(ICompletionProposal proposal) {
			String sortString;
			if(proposal instanceof LangCompletionProposal) {
				LangCompletionProposal langProposal = (LangCompletionProposal) proposal;
				sortString = langProposal.getSortString();
			} else {
				sortString = proposal.getDisplayString();
			}
			return sortString == null ? "\uFFFF" : sortString;
		}
		
	}
	
}