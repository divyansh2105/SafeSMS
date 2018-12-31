package com.example.dahiya.safesms;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class Global {
    public static String password="12345";
    public static BigInteger P;
    public static BigInteger Q;
    public static BigInteger N;
    public static BigInteger T;
    public static BigInteger E;
    public static BigInteger D;


    public Global(String mobile)
    {
        switch (mobile) {
            case "+917678203335":
                this.P = new BigInteger("774192125426797842866761829929");
                this.Q = new BigInteger("932209547344965943489488062113");
                this.N = new BigInteger("721709290802152315749388625348210359324723036638463894379977");
                this.T = new BigInteger("721709290802152315749388625346503957651951272852107644487936");
                this.E = new BigInteger("738990982134857");
                this.D = new BigInteger("216677269889293308792486557814042851342296647753740515646969");
                break;
            case "+919871806344":
                this.P = new BigInteger("1013172872868279245820273373463");
                this.Q = new BigInteger("647503224720269789814470808877");
                this.N = new BigInteger("656032702381310751187474580355957263756862785156675116631051");
                this.T = new BigInteger("656032702381310751187474580354296587659274236121040372448712");
                this.E = new BigInteger("838100859057899");
                this.D = new BigInteger("47721469079152800331149567419940400551222017740822301983955");
                break;
            case "+919810328826":
                this.P = new BigInteger("985455029796804459154480064573");
                this.Q = new BigInteger("672640908143409417320781939433");
                this.N = new BigInteger("662857366177013138523111914534849389595973030405462015007109");
                this.T = new BigInteger("662857366177013138523111914533191293658032816528986753003104");
                this.E = new BigInteger("629839630292027");
                this.D = new BigInteger("555258104002568999529905673458226733268198011543704455482835");
                break;
            case "+919818152732":
                this.P = new BigInteger("714613165493413100776601371439");
                this.Q = new BigInteger("1010597681443330874225747013397");
                this.N = new BigInteger("722186408176522579811275904092327903084921248588368506168283");
                this.T = new BigInteger("722186408176522579811275904090602692237984504613366157783448");
                this.E = new BigInteger("1085732499098393");
                this.D = new BigInteger("221553400082713271008832705327527608972481616829329360373065");
                break;
            case "+918383846605":
                this.P = new BigInteger("1032454942812030008836103577821");
                this.Q = new BigInteger("1168359878433580371003085708807");
                this.N = new BigInteger("1206278931472012555204498507982463895647870404220646469569547");
                this.T = new BigInteger("1206278931472012555204498507980263080826624793840807280282920");
                this.E = new BigInteger("663625953610511");
                this.D = new BigInteger("483390968303501653347310991368797998513084872108555683385871");
                break;
            case "+919650020104":
                this.P = new BigInteger("858975796918143680564838136387");
                this.Q = new BigInteger("898047047324195940423089401471");
                this.N = new BigInteger("771400678145287099335514429787278685953390051111970896425277");
                this.T = new BigInteger("771400678145287099335514429785521663109147711490982968887420");
                this.E = new BigInteger("772257362517713");
                this.D = new BigInteger("322470694165988865789593849779789078101949864305070233497657");
                break;
        }


//        if(mobile.equals("+917678203335"))
//        {
//            this.P=new BigInteger("906894201533");
//            this.Q=new BigInteger("679840446161");
//            this.N=new BigInteger("616543358591018570164813");
//            this.T=new BigInteger("616543358589431835517120");
//            this.E=new BigInteger("551651");
//            this.D=new BigInteger("256938237079090642138251");
//        }
//        if(mobile.equals("+919871806344"))
//        {
//            this.P=new BigInteger("782020851191");
//            this.Q=new BigInteger("762575493971");
//            this.N=new BigInteger("596349936892598708669461");
//            this.T=new BigInteger("596349936891054112324300");
//            this.E=new BigInteger("1043857");
//            this.D=new BigInteger("129991817164854536267093");
//        }
//        else if(mobile.equals("+919810328826"))
//        {
//            this.P=new BigInteger("801764510189");
//            this.Q=new BigInteger("647004076879");
//            this.N=new BigInteger("518744906789177534820131");
//            this.T=new BigInteger("518744906787728766233064");
//            this.E=new BigInteger("792283");
//            this.D=new BigInteger("334796359889227541344843");
//        }
//        else if(mobile.equals("+919818152732"))
//        {
//            this.P=new BigInteger("1067492701879");
//            this.Q=new BigInteger("1000974250121");
//            this.N=new BigInteger("1068532706772972232677359");
//            this.T=new BigInteger("1068532706770903765725360");
//            this.E=new BigInteger("526733");
//            this.D=new BigInteger("401215284047976063549077");
//        }
//        else if(mobile.equals("+918383846605"))
//        {
//            this.P=new BigInteger("922190333221");
//            this.Q=new BigInteger("753253282027");
//            this.N=new BigInteger("694642895152291020318967");
//            this.T=new BigInteger("694642895150615576703720");
//            this.E=new BigInteger("761183");
//            this.D=new BigInteger("300956284069988107936247");
//        }
//        else if(mobile.equals("+919650020104"))
//        {
//            this.P=new BigInteger("910874648969");
//            this.Q=new BigInteger("763583928869");
//            this.N=new BigInteger("695529243166920240186061");
//            this.T=new BigInteger("695529243165245781608224");
//            this.E=new BigInteger("960863");
//            this.D=new BigInteger("386507360055549694465119");
//        }
    }
}
