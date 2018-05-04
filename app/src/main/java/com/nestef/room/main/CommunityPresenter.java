package com.nestef.room.main;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nestef.room.base.BasePresenter;
import com.nestef.room.model.Room;

import java.io.IOException;
import java.util.List;

/**
 * Created by Noah Steffes on 5/2/18.
 */
public class CommunityPresenter extends BasePresenter<MainContract.CommunityView> implements MainContract.CommunityViewActions {
    @Override
    public void fetchRooms() {

        String json = "[\n" +
                "  {\n" +
                "    \"id\": \"539196fc048862e761fa4891\",\n" +
                "    \"name\": \"gitterHQ/docs\",\n" +
                "    \"topic\": \"Gitter API Documentation\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/docs\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 9,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/docs\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 5\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"533354be5e986b0712f00160\",\n" +
                "    \"name\": \"gitterHQ/testing\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/testing\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 3,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/testing\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 5\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"5395cdc8a9176b500d1cd7d8\",\n" +
                "    \"name\": \"gitterHQ/faye\",\n" +
                "    \"topic\": \"Simple pub/sub messaging for the web\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/faye\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 9,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lastAccessTime\": \"2018-04-24T17:03:30.574Z\",\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/faye\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 7\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"52e2a25d5e986b0712ef2fb9\",\n" +
                "    \"name\": \"gitterHQ/rhubarb\",\n" +
                "    \"topic\": \"General Gitter chat about whatever\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/rhubarb\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 62,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/rhubarb\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 126\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"53df8f57107e137846ba9159\",\n" +
                "    \"name\": \"gitterHQ/mike-test\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/mike-test\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 2,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/mike-test\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 3\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"534ec2155e986b0712f0372f\",\n" +
                "    \"name\": \"gitterHQ/node-gitter\",\n" +
                "    \"topic\": \"Gitter API Client\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/node-gitter\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 32,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/node-gitter\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 71\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"53ff5471163965c9bc20107a\",\n" +
                "    \"name\": \"gitterHQ/billing-test\",\n" +
                "    \"topic\": \"Help us test out billing, earn great rewards and such karma\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/billing-test\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 12,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/billing-test\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 15\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"5304c4f15e986b0712ef7e2c\",\n" +
                "    \"name\": \"gitterHQ/mutant.js\",\n" +
                "    \"topic\": \"Watch for mutations\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/mutant.js\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 21,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/mutant.js\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 23\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"543be0dddb8155e6700cb09d\",\n" +
                "    \"name\": \"gitterHQ/gitter-demo-app\",\n" +
                "    \"topic\": \"Gitter Demo App\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/gitter-demo-app\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 3,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/gitter-demo-app\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"534ea47e5e986b0712f036ff\",\n" +
                "    \"name\": \"gitterHQ/gitter-ruby-stream\",\n" +
                "    \"topic\": \"Gitter Stream API example client\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/gitter-ruby-stream\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 4,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/gitter-ruby-stream\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 9\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"53dbb833107e137846ba8d54\",\n" +
                "    \"name\": \"gitterHQ/marked\",\n" +
                "    \"topic\": \"A fork of the marked parser, customised for Gitter. Highly recommend using the original marked parser over this fork\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/marked\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 3,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/marked\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 9\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"532c637f5e986b0712eff659\",\n" +
                "    \"name\": \"gitterHQ/services\",\n" +
                "    \"topic\": \"The things that power your Gitter activity feed\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/services\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 143,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lastAccessTime\": \"2018-04-17T21:49:54.586Z\",\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/services\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 261\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"5375ca3e048862e761fa177f\",\n" +
                "    \"name\": \"gitterHQ/gitter/chrome-app\",\n" +
                "    \"topic\": \"Any issues/suggestions with the Chrome App, please let us know in here\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/gitter/chrome-app\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 6,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/gitter/chrome-app\",\n" +
                "    \"githubType\": \"REPO_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 11\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"54510d0adb8155e6700cf712\",\n" +
                "    \"name\": \"gitterHQ/foobar\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/foobar\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 5,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/foobar\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 3\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"5395ce10a9176b500d1cd7dc\",\n" +
                "    \"name\": \"gitterHQ/shutdown\",\n" +
                "    \"topic\": \"Sequenced shutdown events for graceful shutdown in Node\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/shutdown\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 5,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/shutdown\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 5\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"533aa1485e986b0712f00ba5\",\n" +
                "    \"name\": \"gitterHQ/developers\",\n" +
                "    \"topic\": \"A general chat for developers. Don't ask to ask, just ask.\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/developers\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 7837,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lastAccessTime\": \"2018-03-09T18:11:19.451Z\",\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/developers\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 638\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"54ac1988db8155e6700e68c0\",\n" +
                "    \"name\": \"gitterHQ/notifylurk\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/notifylurk\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 5,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/notifylurk\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 2\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"52b42a52ed5ab0b3bf051b93\",\n" +
                "    \"name\": \"gitterHQ/sandbox\",\n" +
                "    \"topic\": \"this is a really really long topic message that should be longer than the length of the window and i'd like to see what happens when that happens because i think it will be a problem\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/sandbox\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 167,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/sandbox\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 214\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"54807ab4db8155e6700db6aa\",\n" +
                "    \"name\": \"gitterHQ/vat-calculator\",\n" +
                "    \"topic\": \"Calculate European VAT rates\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/vat-calculator\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 9,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/vat-calculator\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 3\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"53ff4e0c163965c9bc201063\",\n" +
                "    \"name\": \"gitterHQ/public-test\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/public-test\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 9,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/public-test\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 9\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"54984565db8155e6700e20b5\",\n" +
                "    \"name\": \"gitterHQ/desktop\",\n" +
                "    \"topic\": \"https://gitlab.com/gitlab-org/gitter/desktop/ for bugs and downloads\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/desktop\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 128,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/desktop\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 196\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"5513e9cf15522ed4b3dddaea\",\n" +
                "    \"name\": \"gitterHQ/blowup\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/blowup\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 2,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/blowup\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"535a93484f309340acf568f7\",\n" +
                "    \"name\": \"gitterHQ/-----marquee--img-src-x-onerror-confirm-1----marquee-----plaintext--------plaintext-onmouseover-prom\",\n" +
                "    \"topic\": \"'\\\">><marquee><img src=x onerror=confirm(1)></marquee>\\\"></plaintext\\\\></|\\\\><plaintext/onmouseover=prompt(1)>\\\"><script>alert(document.domain)</script>@gmail.com<isindex formaction=javascript:alert(/XSS/) type=submit>'-->\\\"></script><script>alert(1)</script>\\\"><img/id=\\\"confirm&lpar;1&#x29;\\\"/alt=\\\"/\\\"src=\\\"/\\\"onerror=eval(id&#x29;>'\\\"><img src=\\\"http://bryanhallsawakening.files.wordpress.com/2013/09/anonymousbigbrotherclone.jpg\\\"/ onmousover=alert(1)><script/&Tab; src='https://dl.dropbox.com/u/13018058/js.js' /&Tab;></script>  '\\\">><marquee><img src=x onerror=confirm(1)></marquee>\\\"></plaintext\\\\></|\\\\><plaintext/onmouseover=prompt(1)>\\\"><script>alert(document.domain)</script>@gmail.com<isindex formaction=javascript:alert(/XSS/) type=submit>'-->\\\"></script><script>alert(1)</script>\\\"><img/id=\\\"confirm&lpar;1&#x29;\\\"/alt=\\\"/\\\"src=\\\"/\\\"onerror=eval(id&#x29;>'\\\"><img src=\\\"http://xssplayground.net23.net/xssimagefile2.svg\\\"/onmousover=alert(1)><script/&Tab; src='https://dl.dropbox.com/u/13018058/js.js' /&Tab;></script>    \\\">><marquee><img src=x onerror=confirm(1)></marquee>\\\"></plaintext\\\\></|\\\\><plaintext/onmouseover=prompt(1)>\\\"><script>alert(document.domain)</script>@gmail.com<isindex formaction=javascript:alert(/XSS/) type=submit>'-->\\\"></script><script>alert(1)</script>\\\"><img/id=\\\"confirm&lpar;1&#x29;\\\"/alt=\\\"/\\\"src=\\\"/\\\"onerror=eval(id&#x29;>'\\\"><img src=\\\"http://xssplayground.net23.net/xssimagefile2.svg\\\"/onmousover=alert(1)><embed/&Tab; src='https://xssplayground.net23.net/xss.swf'>\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/-----marquee--img-src-x-onerror-confirm-1----marquee-----plaintext--------plaintext-onmouseover-prom\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 12,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/-----marquee--img-src-x-onerror-confirm-1----marquee-----plaintext--------plaintext-onmouseover-prom\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 9\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"5536224a15522ed4b3df4ca6\",\n" +
                "    \"name\": \"gitterHQ/bantest\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/bantest\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 5,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/bantest\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 3\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"5540e3fc15522ed4b3dfaf7c\",\n" +
                "    \"name\": \"gitterHQ/irc-test\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/irc-test\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 9,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/irc-test\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 6\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"541ab67e163965c9bc2054b3\",\n" +
                "    \"name\": \"gitterHQ/gitter-translations\",\n" +
                "    \"topic\": \"Translations of Gitter to other languages. Please submit pull-requests if you would like to add or change any of our translations.\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/gitter-translations\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 34,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/gitter-translations\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 29\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"552b9a0c15522ed4b3deeca4\",\n" +
                "    \"name\": \"gitterHQ/gitter/alo\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/gitter/alo\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 1,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/gitter/alo\",\n" +
                "    \"githubType\": \"REPO_CHANNEL\",\n" +
                "    \"security\": \"INHERITED\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 3\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"552b9cc815522ed4b3deecc3\",\n" +
                "    \"name\": \"gitterHQ/gitter/test2\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/gitter/test2\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 1,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/gitter/test2\",\n" +
                "    \"githubType\": \"REPO_CHANNEL\",\n" +
                "    \"security\": \"INHERITED\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"552b9d3c15522ed4b3deecc6\",\n" +
                "    \"name\": \"gitterHQ/gitter/test3\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/gitter/test3\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 4,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/gitter/test3\",\n" +
                "    \"githubType\": \"REPO_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 2\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"53f363d1163965c9bc1fee8b\",\n" +
                "    \"name\": \"gitterHQ/ansible\",\n" +
                "    \"topic\": \"Ansible is a radically simple IT automation platform that makes your applications and systems easier to deploy. Avoid writing scripts or custom code to deploy and update your applications— automate in a language that approaches plain English, using SSH, with no agents to install on remote systems.\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/ansible\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 275,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/ansible\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 70\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"54eb78b215522ed4b3dc5fd7\",\n" +
                "    \"name\": \"gitterHQ/irc-bridge\",\n" +
                "    \"topic\": \"Connect to Gitter using the IRC protocol, https://gitlab.com/gitlab-org/gitter/irc-bridge\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/irc-bridge\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 101,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/irc-bridge\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 62\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"554e535915522ed4b3e02924\",\n" +
                "    \"name\": \"gitterHQ/bob\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/bob\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 2,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/bob\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 3\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"529c94dded5ab0b3bf04e16e\",\n" +
                "    \"name\": \"gitterHQ/gitter\",\n" +
                "    \"topic\": \"Gitter support – please visit http://support.gitter.im and https://gitlab.com/gitlab-org/gitter/webapp/issues before chatting\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/gitter\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 6705,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lastAccessTime\": \"2018-04-22T20:45:00.423Z\",\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/gitter\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 14798\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"555224f115522ed4b3e04761\",\n" +
                "    \"name\": \"gitterHQ/gitter/anyonecanjoin\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/gitter/anyonecanjoin\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 2,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/gitter/anyonecanjoin\",\n" +
                "    \"githubType\": \"REPO_CHANNEL\",\n" +
                "    \"security\": \"INHERITED\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"530742355e986b0712ef9df3\",\n" +
                "    \"name\": \"gitterHQ/autolink\",\n" +
                "    \"topic\": \"Autolink http(s) links in the DOM\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/autolink\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 7,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/autolink\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 12\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"53b160a7107e137846ba4e38\",\n" +
                "    \"name\": \"gitterHQ/nodejs\",\n" +
                "    \"topic\": \"A general chinwag about Node things, please refrain from recruitment. Please speak only in English.\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/nodejs\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 5270,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/nodejs\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 11121\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"557b0a2715522ed4b3e1d2a1\",\n" +
                "    \"name\": \"gitterHQ/test\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/test\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 3,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/test\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 2\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"541035d1163965c9bc203bb9\",\n" +
                "    \"name\": \"gitterHQ/javascript\",\n" +
                "    \"topic\": \"A general chat about JavaScript. Ask anything you like or share your best cat picture. Please check Google/StackOverflow for answers before asking for help. And remember, be nice!\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/javascript\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 872,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/javascript\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 65\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"54eb780d15522ed4b3dc5fc7\",\n" +
                "    \"name\": \"gitterHQ/tentacles\",\n" +
                "    \"topic\": \"A node.js client for GitHub\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/tentacles\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 6,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/tentacles\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 3\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"55e592740fc9f982beaf4774\",\n" +
                "    \"name\": \"gitterHQ/sidecar\",\n" +
                "    \"topic\": \"Gitter embed widget\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/sidecar\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 34,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/sidecar\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"55ffe5e50fc9f982beb15300\",\n" +
                "    \"name\": \"gitterHQ/hitchhikers\",\n" +
                "    \"topic\": \"Site for GitHub Universe\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/hitchhikers\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 4,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/hitchhikers\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"560c1c63d33f749381a7d66c\",\n" +
                "    \"name\": \"gitterHQ/sidecar-demo\",\n" +
                "    \"topic\": \"https://sidecar.gitter.im/\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/sidecar-demo\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 410,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/sidecar-demo\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"5638b51e16b6c7089cb94dce\",\n" +
                "    \"name\": \"gitterHQ/butwhytest\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/butwhytest\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 1,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/butwhytest\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"563b935716b6c7089cb99cab\",\n" +
                "    \"name\": \"gitterHQ/testmore\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/testmore\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 3,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/testmore\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"563b942e16b6c7089cb99cd0\",\n" +
                "    \"name\": \"gitterHQ/JPTesting\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/JPTesting\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 3,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/JPTesting\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"564145c816b6c7089cba1c84\",\n" +
                "    \"name\": \"gitterHQ/halley\",\n" +
                "    \"topic\": \"An experimental bayeux client for modern browsers and nodejs. Forked from Faye.\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/halley\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 9,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/halley\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"564b4adb16b6c7089cbb0d07\",\n" +
                "    \"name\": \"gitterHQ/ansible-ec2-inventory\",\n" +
                "    \"topic\": \"Ansible dynamic inventory that uses EC2 tags to keep track of names and groups\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/ansible-ec2-inventory\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 5,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/ansible-ec2-inventory\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"568ffac316b6c7089cc1ba4b\",\n" +
                "    \"name\": \"gitterHQ/ghdemo\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/ghdemo\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 1,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/ghdemo\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"569514f616b6c7089cc23363\",\n" +
                "    \"name\": \"gitterHQ/lag\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/lag\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 8,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/lag\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"569e1529e610378809bd12f4\",\n" +
                "    \"name\": \"gitterHQ/publictest123\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/publictest123\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 0,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/publictest123\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"56b32ca8e610378809bfd3b8\",\n" +
                "    \"name\": \"gitterHQ/gitterHQ.github.io\",\n" +
                "    \"topic\": \"Troupe engineering blog\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/gitterHQ.github.io\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 6,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/gitterHQ.github.io\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"56bb255ce610378809c0be31\",\n" +
                "    \"name\": \"gitterHQ/iOS_view_controllers\",\n" +
                "    \"topic\": \"http://blog.gitter.im/2016/02/10/how-to-cache-view-controllers-in-ios/\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/iOS_view_controllers\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 10,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/iOS_view_controllers\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"56e6eb3785d51f252ab8bbe1\",\n" +
                "    \"name\": \"gitterHQ/change\",\n" +
                "    \"topic\": \"Up-coming changes to Gitter - http://blog.gitter.im/2016/03/15/introducing-our-new-ui/\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/change\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 51,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/change\",\n" +
                "    \"githubType\": \"REPO\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true,\n" +
                "    \"v\": 2\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"5718b26a659847a7aff3d4ff\",\n" +
                "    \"name\": \"gitterHQ/whatbob\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/whatbob\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 2,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/whatbob\",\n" +
                "    \"githubType\": \"ORG_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"5790a3a2c2f0db084a24004d\",\n" +
                "    \"name\": \"gitterHQ/api\",\n" +
                "    \"topic\": \"Gitter API and Libraries\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/api\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 86,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lastAccessTime\": \"2018-04-22T20:45:00.539Z\",\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/api\",\n" +
                "    \"githubType\": \"REPO_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": true,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"57f3843ad73408ce4f2bac1a\",\n" +
                "    \"name\": \"gitterHQ/topics-feedback\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/topics-feedback\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 2,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/topics-feedback\",\n" +
                "    \"githubType\": \"REPO_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"589862d2d73408ce4f48689d\",\n" +
                "    \"name\": \"gitterHQ/muchtest22\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/muchtest22\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 1,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/muchtest22\",\n" +
                "    \"githubType\": \"REPO_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"595a1af7d73408ce4f6b5310\",\n" +
                "    \"name\": \"gitterHQ/contributing\",\n" +
                "    \"topic\": \"\",\n" +
                "    \"avatarUrl\": \"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\n" +
                "    \"uri\": \"gitterHQ/contributing\",\n" +
                "    \"oneToOne\": false,\n" +
                "    \"userCount\": 17,\n" +
                "    \"unreadItems\": 0,\n" +
                "    \"mentions\": 0,\n" +
                "    \"lastAccessTime\": \"2018-03-11T22:02:55.384Z\",\n" +
                "    \"lurk\": false,\n" +
                "    \"url\": \"/gitterHQ/contributing\",\n" +
                "    \"githubType\": \"REPO_CHANNEL\",\n" +
                "    \"security\": \"PUBLIC\",\n" +
                "    \"premium\": true,\n" +
                "    \"noindex\": false,\n" +
                "    \"roomMember\": false,\n" +
                "    \"groupId\": \"57542c12c43b8c601976fa66\",\n" +
                "    \"public\": true\n" +
                "  }\n" +
                "]";
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Room> rooms = mapper.readValue(json, new TypeReference<List<Room>>() {
            });
            List<Room> roms2 = rooms.subList(10, 15);

            mView.showJoinedRooms(rooms);
            mView.showUnjoinedRooms(roms2);

            // need to make 2 lists , one with joined rooms and the other with unjoined rooms
            //can iterate over it with a loop
            //or use guava  with a predicate , would also help with sorting unread messages
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @Override
    public void fetchJoinedRooms() {

    }

    @Override
    public void fetchUnjoinedRooms() {

    }
}
