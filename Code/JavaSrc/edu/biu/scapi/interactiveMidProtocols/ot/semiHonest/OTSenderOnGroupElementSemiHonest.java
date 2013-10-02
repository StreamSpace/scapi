/**
* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
* 
* Copyright (c) 2012 - SCAPI (http://crypto.biu.ac.il/scapi)
* This file is part of the SCAPI project.
* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
* to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
* and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
* FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
* WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
* 
* We request that any publication and/or code referring to and/or based on SCAPI contain an appropriate citation to SCAPI, including a reference to
* http://crypto.biu.ac.il/SCAPI.
* 
* SCAPI uses Crypto++, Miracl, NTL and Bouncy Castle. Please see these projects for any further licensing issues.
* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
* 
*/
package edu.biu.scapi.interactiveMidProtocols.ot.semiHonest;

import java.security.SecureRandom;

import edu.biu.scapi.exceptions.SecurityLevelException;
import edu.biu.scapi.interactiveMidProtocols.ot.OTSInput;
import edu.biu.scapi.interactiveMidProtocols.ot.OTSMessage;
import edu.biu.scapi.interactiveMidProtocols.ot.OTSOnGroupElementInput;
import edu.biu.scapi.primitives.dlog.DlogGroup;
import edu.biu.scapi.primitives.dlog.GroupElement;
import edu.biu.scapi.securityLevel.SemiHonest;

/**
 * Concrete class for Semi-Honest OT assuming DDH sender ON GROUP ELEMENT.
 * This class derived from OTSenderDDHSemiHonestAbs and implements the functionality 
 * related to the GroupElement inputs.
 * 
 * @author Cryptography and Computer Security Research Group Department of Computer Science Bar-Ilan University (Moriya Farbstein)
 *
 */
public class OTSenderOnGroupElementSemiHonest extends OTSenderDDHSemiHonestAbs implements SemiHonest{
	
	/**
	 * Constructor that chooses default values of DlogGroup and SecureRandom.
	 */
	public OTSenderOnGroupElementSemiHonest(){
		super();
	}
	
	/**
	 * Constructor that sets the given dlogGroup and random.
	 * @param dlog must be DDH secure.
	 * @param random
	 * @throws SecurityLevelException if the given DlogGroup is not DDH secure.
	 */
	public OTSenderOnGroupElementSemiHonest(DlogGroup dlog, SecureRandom random) throws SecurityLevelException{
		super(dlog, random);
	}
	
	/**
	 * Runs the following lines from the protocol:
	 * "COMPUTE:
	 *		�	v0 = x0 * k0
	 *		�	v1 = x1 * k1"
	 * @param input MUST be an instance of OTSOnGroupElementInput
	 * @param k1 
	 * @param k0 
	 * @param u 
	 * @return tuple contains (u, v0, v1) to send to the receiver.
	 */
	protected OTSMessage computeTuple(OTSInput input, GroupElement u, GroupElement k0, GroupElement k1) {
		//If input is not instance of OTSOnGroupElementInput, throw Exception.
		if (!(input instanceof OTSOnGroupElementInput)){
			throw new IllegalArgumentException("x0 and x1 should be DlogGroup elements.");
		}
				
		//Set x0, x1.
		GroupElement x0 = ((OTSOnGroupElementInput) input).getX0();
		GroupElement x1 = ((OTSOnGroupElementInput) input).getX1();
				
		//Calculate v0:
		GroupElement v0 = dlog.multiplyGroupElements(x0, k0);
		
		//Calculate v1:
		GroupElement v1 = dlog.multiplyGroupElements(x1, k1);
		
		//Create and return sender message.
		return new OTSOnGroupElementSemiHonestMessage(u.generateSendableData(), v0.generateSendableData(), v1.generateSendableData());
	}
}
