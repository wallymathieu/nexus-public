/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2008-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.repository.view.handlers;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.WritePolicyConflictException;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Request;
import org.sonatype.nexus.repository.view.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

public class ExceptionHandlerTest
    extends TestSupport
{
  private final ExceptionHandler subject = new ExceptionHandler();

  @Mock
  private Context context;

  @Mock
  private Request request;

  @Before
  public void setUp() throws Exception {
    when(context.getRequest()).thenReturn(request);
    when(request.getAction()).thenReturn("PUT");
    when(request.getPath()).thenReturn("/package.nupkg");
  }

  @Test
  public void returnsConflictForWritePolicyConflict() throws Exception {
    when(context.proceed()).thenThrow(new WritePolicyConflictException("already exists"));

    Response response = subject.handle(context);

    assertThat(response.getStatus().getCode(), equalTo(409));
    assertThat(response.getStatus().getMessage(), equalTo("already exists"));
  }
}
