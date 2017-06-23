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
 * This class denotes unread conversations and messages to be shown to the user.
 * @author Xinyu Liu, Yue Hu
 */
public class Conversations {

    /**
     * Set of strings used as messages.
     */
    private static final String[] MESSAGES = new String[]{
            "Do you want to block your parking saver?",
            "Do you want to unblock your parking saver?",
            "Now,you are at the nearby of your reserved"+ "number" +String.valueOf(MessagingFragment.getPs_ID()) +"parkingsaver, Do you want to unblocked it?"
    };

    /**
     * Senders of the said messages.
     */
    private static final String[] PARTICIPANTS = new String[]{
            "parking saver service"
    };


    static class Conversation {

        private final int conversationId;

        private final String participantName;

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
