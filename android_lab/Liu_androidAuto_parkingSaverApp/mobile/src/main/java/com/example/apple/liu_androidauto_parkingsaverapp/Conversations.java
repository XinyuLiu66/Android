/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.apple.liu_androidauto_parkingsaverapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A simple class that denotes unread conversations and messages. In a real world application,
 * this would be replaced by a content provider that actually gets the unread messages to be
 * shown to the user.
 */
public class Conversations {

    /**
     * Set of strings used as messages by the sample.
     */
    private static final String[] MESSAGES = new String[]{
            /*"Are you at home?",
            "Can you give me a call?",
            "Hey yt?",
            "Don't forget to get some milk on your way back home",
            "Is that project done?",
            "Did you finish the Messaging app yet?"*/
            "Do you want to block your parking saver?",
            "Do you want to unblock your parking saver?",
            "Now,you are at the nearby of your reserved"+ "number" +String.valueOf(MessagingFragment.getPs_ID()) +"parkingsaver, Do you want to unblocked it?"
    };
//    private static final String[] MESSAGES_unblock = new String[]{
//            /*"Are you at home?",
//            "Can you give me a call?",
//            "Hey yt?",
//            "Don't forget to get some milk on your way back home",
//            "Is that project done?",
//            "Did you finish the Messaging app yet?"*/
//            "Do you want to block your parking saver?",
//            "Do you want to unblock your parking saver?"
//    };

    /**
     * Senders of the said messages.
     */
    private static final String[] PARTICIPANTS = new String[]{
            /*"John Smith",
            "Robert Lawrence",
            "James Smith",
            "Jane Doe"*/
            "parking saver service"
    };

    static class Conversation {

        private final int conversationId;

        private final String participantName;

        /**
         * A given conversation can have a single or multiple messages.
         * Note that the messages are sorted from *newest* to *oldest*
         */
        private final List<String> messages;

        private final long timestamp;

        public Conversation(int conversationId, String participantName,
                            List<String> messages) {
            this.conversationId = conversationId;
            this.participantName = participantName;
            this.messages = messages == null ? Collections.<String>emptyList() : messages;
            this.timestamp = System.currentTimeMillis();
        }

        public int getConversationId() {
            return conversationId;
        }

        public String getParticipantName() {
            return participantName;
        }

        public List<String> getMessages() {
            return messages;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public String toString() {
            return "[Conversation: conversationId=" + conversationId +
                    ", participantName=" + participantName +
                    ", messages=" + messages +
                    ", timestamp=" + timestamp + "]";
        }
    }

    private Conversations() {
    }

    public static Conversation[] getUnreadConversations(int howManyConversations,
                                                        int indicate_to_block) {
        Conversation[] conversations = new Conversation[howManyConversations];
        for (int i = 0; i < howManyConversations; i++) {
            conversations[i] = new Conversation(

                    ThreadLocalRandom.current().nextInt(),
                    name(), makeMessages(indicate_to_block));
        }
        return conversations;
    }

//    private static List<String> makeMessages(int messagesPerConversation) {
//        int maxLen = MESSAGES.length;
//        List<String> messages = new ArrayList<>(messagesPerConversation);
//        for (int i = 0; i < messagesPerConversation; i++) {
//            messages.add(MESSAGES[ThreadLocalRandom.current().nextInt(0, maxLen)]);
//        }
//        return messages;
//    }

    //==========version2================
    private static List<String> makeMessages(int indicate_to_block) {

        List<String> messages = new ArrayList<>();
        if(indicate_to_block == 0) {
            messages.add(MESSAGES[0]);
        }  else if (indicate_to_block ==1) {
            messages.add(MESSAGES[1]);
        } else if(indicate_to_block ==2) {
            messages.add(MESSAGES[2]);
        }
        return  messages;

    }

    private static String name() {
        return PARTICIPANTS[ThreadLocalRandom.current().nextInt(0, PARTICIPANTS.length)];
    }
}
