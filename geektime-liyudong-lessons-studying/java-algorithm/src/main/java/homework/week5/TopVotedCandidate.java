package homework.week5;

import java.util.*;

public class TopVotedCandidate {

    private List<Vote> votes = new ArrayList<>();

    public TopVotedCandidate(int[] persons,int[] times){
        Map<Integer, Integer> count = new HashMap<>();

        int leader = -1;
        int leaderNumber = 0;

        for(int i=0;i<persons.length;++i){
            int person = persons[i],time=times[i];
            int c = count.getOrDefault(person,0)+1;
            count.put(person,c);

            if (c>=leaderNumber){
                if (person!=leader){
                    leader = person;
                    votes.add(new Vote(leader,time));
                }

                if (c>leaderNumber){
                    leaderNumber = c;
                }
            }
        }
    }

    //二分搜索
    public  int q (int t){
        int count=1,length=votes.size();
        while(count<length){
            int mi = count + (length-count)/2;
            if (votes.get(mi).time>=t){
                count = mi + 1;
            }else{
                length = mi;
            }
        }
        return votes.get(count-1).person;
    }

    class Vote{
        int person,time;

        Vote(int p,int t){
            this.person = p;
            this.time = t;
        }
    }

}


