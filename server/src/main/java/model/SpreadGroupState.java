package model;

import core.PrimaryServerHandler;
import spread.GroupID;
import spread.SpreadGroup;

import java.util.Arrays;

public class SpreadGroupState {
    public static SpreadGroup myGroup;
    public static SpreadGroup groupName;
    public static GroupID groupID;
    public static long groupSize;
    public static SpreadGroup[] groupMembers;

    public static void print() {
        System.out.printf("{groupName: %s, ", groupName.toString());
        System.out.printf("groupID: %s, ", groupID.toString());
        System.out.printf("groupSize: %s, ", groupSize);
        System.out.print("groupMembers: [");
        Arrays.stream(groupMembers).forEach(spreadGroup -> System.out.printf("{memberName: %s}", spreadGroup.toString()));
        System.out.print("]}\n");
    }

    public static void setGroupMembers(SpreadGroup[] members){
        groupMembers = members;

        System.out.print("Setting group members in local state: ");
        Arrays.stream(groupMembers).forEach(groupName -> {
            System.out.printf(" %s ", groupName.toString());
        });
        System.out.print("\n");
    }
}
