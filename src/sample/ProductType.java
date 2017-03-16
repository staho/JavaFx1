package sample;

/**
 * Created by staho on 16.03.2017.
 */
public enum ProductType {
    DEFAULT,
    FOOD{
        @Override
        public String toString(){
            return "Jedzenie";
        }
    },
    DRUG{
        @Override
        public String toString(){
            return "Lekarstwo";
        }
    },
    SWEETS{
        @Override
        public String toString(){
            return "Słodycze";
        }
    },
    DRINK{
        @Override
        public String toString(){
            return "Napój";
        }
    };
    @Override
    public String toString(){
        return super.toString().toLowerCase();
    }

}
