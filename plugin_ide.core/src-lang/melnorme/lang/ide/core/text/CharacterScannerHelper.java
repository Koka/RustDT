/*******************************************************************************
 * Copyright (c) 2015 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package melnorme.lang.ide.core.text;

import org.eclipse.jface.text.rules.ICharacterScanner;

import melnorme.lang.tooling.parser.lexer.AbstractCharacterReader;
import melnorme.lang.tooling.parser.lexer.ICharacterReader;

/**
 * Adapt {@link ICharacterReader} over a {@link ICharacterScanner}
 */
public class CharacterScannerHelper extends AbstractCharacterReader {

	protected final ICharacterScanner scanner;
	
	public CharacterScannerHelper(ICharacterScanner scanner) {
		this.scanner = scanner;
	}
	
	@Override
	protected int doRead() {
		return scanner.read();
	}
	
	@Override
	protected void doUnread() {
		scanner.unread();
	}
	
}