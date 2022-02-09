/**
 *    Copyright 2022 Joshua Zierman
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package us.zierman.joshua.phillsquill;

import javax.swing.*;

public class ApplicationConstants {
    public static final JTextArea ABOUT_MESSAGE_TEXT_AREA = createAboutMessageTextArea();

    private static JTextArea createAboutMessageTextArea(){
        {
            JTextArea textArea = new JTextArea(
                    """
                            Program Name: Phill's Quill
                            GitHub Page: https://github.com/Zierman/phillsquill
                            License: Apache License Version 2.0, January 2004
                                        
                            Hi. My name is Joshua Zierman, the initial author of Phill's Quill.
                                        
                            When I decided to create this tool, I had two motivations:
                                        
                                Firstly I wanted to practice writing Java and play with things that I haven't ued before.
                                        
                                Secondly I wanted to create a tool that would allow my D&D playgroup to convert notes written in MS Word into
                                hard-wrapped plain text so it is more readable when posted in our text chat channel.
                            
                            I hope you find this program useful. If you find any bugs or have a request, please
                            file an issue at https://github.com/Zierman/phillsquill/issues/new
                            """);
            textArea.setEditable(false);
            return textArea;
        }
    }
}
