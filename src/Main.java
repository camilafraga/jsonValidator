import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> lstJsontoValidate = new ArrayList<>();
        lstJsontoValidate.add("teste {[]}");//valid
        lstJsontoValidate.add("{ teste {[]}}");//valid
        lstJsontoValidate.add("{}[]");//valid
        lstJsontoValidate.add("[{[]}]");//valid
        lstJsontoValidate.add("{[]}{}[{1}{}]");//valid

        lstJsontoValidate.add("{ teste {[]}");//invalid
        lstJsontoValidate.add("{[}[]");//invalid
        lstJsontoValidate.add("[{[{]}]");//invalid
        lstJsontoValidate.add("{[]}{}[{1}{}]{");//invalid
        lstJsontoValidate.add("[[[[]]]");//invalid
        lstJsontoValidate.add("{{{{}");//invalid

        int colchetesAberto = 0;
        int colchetesFechado = 0;
        int chavetaAberta = 0;
        int chavetaFechada = 0;
        boolean jsonInvalid = false;

        for (String jsontoValidate : lstJsontoValidate) {
            String[] wordsToTranslate = jsontoValidate.split("");

            for (int i = 0; i < wordsToTranslate.length; i++) {
                if (wordsToTranslate[i].contentEquals("[")) {
                    colchetesAberto++;
                } else if (wordsToTranslate[i].contentEquals("]")) {
                    colchetesFechado++;
                    //se não for o primeiro caractere, valida se o caractere anterior é uma abertura de colchete
                    //se for já passa para o próximo caractere pois está validado que o colchete abriu e logo fechou
                    if (i >=1 && !wordsToTranslate[i - 1].equals("[")) {
                        //se tiver mais de um conchete aberto
                        // e o numero de aberturas dos colchetes diferentes dos fechamentos e
                        // e o numero de aberturas dos chavetas diferentes dos fechamentos
                        if ((colchetesAberto > 1
                                && colchetesAberto != colchetesFechado
                                && chavetaAberta != chavetaFechada) ||
                                //ou os fechamentos dos colchetes for maior que o número de aberturas será dado como inválido
                                colchetesFechado > colchetesAberto) {
                            jsonInvalid = true;
                            break;
                        }
                    }
                } else if (wordsToTranslate[i].contentEquals("{")) {
                    chavetaAberta++;
                } else if (wordsToTranslate[i].contentEquals("}")) {
                    chavetaFechada++;
                    //se não for o primeiro caractere, valida se o caractere anterior é uma abertura de chaveta
                    //se for já passa para o próximo caractere pois está validado que o chaveta abriu e logo fechou
                    if (i >=1 && !wordsToTranslate[i - 1].equals("{")) {
                        //se o numero de aberturas for diferentes dos fechamentos e já estivermos no fim da string será dado como inválido
                        if (((chavetaAberta != chavetaFechada) && i+1 == wordsToTranslate.length)) {
                            jsonInvalid = true;
                            break;
                        }
                    }
                }
            }


            if (jsonInvalid || colchetesAberto != colchetesFechado || chavetaAberta != chavetaFechada) {
                System.out.println("Json '"+jsontoValidate+"' inválido!");
            } else {
                System.out.println("Json '"+jsontoValidate+"' válido!");
            }
        }

    }
}