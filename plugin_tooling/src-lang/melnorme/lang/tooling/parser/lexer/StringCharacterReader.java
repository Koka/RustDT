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
package melnorme.lang.tooling.parser.lexer;

import static melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;

public class StringCharacterReader implements ICharacterReader {
	
	protected final String source;
	protected int offset = 0;
	
	public StringCharacterReader(String source) {
		this.source = source;
	}
	
	public String getSource() {
		return source;
	}
	
	public int getOffset() {
		return offset;
	}
	
	protected void invariant() {
		assertTrue(offset >= 0);
	}
	
	@Override
	public int lookahead() {
		invariant();
		
		if(offset >= source.length()) {
			return -1;
		}
		return source.charAt(offset);
	}
	
	public boolean lookaheadIsEOF() {
		return lookahead() == -1;
	}
	
	@Override
	public int read() {
		int ch = lookahead();
		offset++;
		return ch;
	}
	
	@Override
	public void unread() {
		offset--;
		invariant();
	}
	
	@Override
	public void reset() {
		offset = 0;
	}
	
	@Override
	public boolean consume(int character) {
		if(lookahead() == character) {
			read();
			return true;
		}
		return false;
	}
	
}