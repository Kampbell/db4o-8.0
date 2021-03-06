/* This file is part of the db4o object database http://www.db4o.com

Copyright (C) 2004 - 2011  Versant Corporation http://www.versant.com

db4o is free software; you can redistribute it and/or modify it under
the terms of version 3 of the GNU General Public License as published
by the Free Software Foundation.

db4o is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License along
with this program.  If not, see http://www.gnu.org/licenses/. */
package com.db4o.db4ounit.common.concurrency;

import com.db4o.*;
import com.db4o.db4ounit.common.persistent.*;
import com.db4o.ext.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class ReadObjectQBETestCase extends Db4oClientServerTestCase {

	public static void main(String[] args) {
		new ReadObjectQBETestCase().runConcurrency();
	}

	private static String testString = "simple test string";

	protected void store() {
		for (int i = 0; i < threadCount(); i++) {
			store(new SimpleObject(testString + i, i));
		}
	}
	
	public void concReadSameObject(ExtObjectContainer oc) throws Exception {
		int mid = threadCount() / 2;
		SimpleObject example = new SimpleObject(testString + mid, mid);
		ObjectSet result = oc.queryByExample(example);
		Assert.areEqual(1, result.size());
		Assert.areEqual(example, result.next());
	}

	public void concReadDifferentObject(ExtObjectContainer oc, int seq)
			throws Exception {
		SimpleObject example = new SimpleObject(testString + seq, seq);
		ObjectSet result = oc.queryByExample(example);
		Assert.areEqual(1, result.size());
		Assert.areEqual(example, result.next());
	}

}
