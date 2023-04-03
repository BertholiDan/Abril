import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
   
    public static void main(String[] args) throws Exception {
    
        // fazer uma conexão HTTP e buscar os top 250 filmes
        String url = "https://gist.githubusercontent.com/lucasfugisawa/cbb0d10ee3901bd0541468e431c629b3/raw/1fe1f3340dfe5b5876a209c0e8226d090f6aef10/Top250Movies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        System.out.println();
        System.out.println(listaDeFilmes.size());
        System.out.println();
        System.out.println(listaDeFilmes.get(0));
        System.out.println();
        
        // exibir e manipular os dados 
        var geradora = new GeradoraDeFigurinhas();
        for (int index = 0; index < 6; index++) {
            var filme = listaDeFilmes.get(index);
 //       }
 //       for (Map<String,String> filme : listaDeFilmes) {

            String urlImagem = filme.get("image");
            String titulo = filme.get("title");

            var diretorio = new File("figurinhas/");
            diretorio.mkdir();

            InputStream inputStream = new URL(urlImagem).openStream();
            String nomeArquivo = "figurinhas/" + titulo + ".png";

            
            geradora.cria(inputStream, nomeArquivo);

            System.out.println(titulo);
            System.out.println();
        }
            

        /**for (int i = 0; i < 25; i++) {
            Map<String,String> filme = listaDeFilmes.get(i);
            System.out.println();
            System.out.println("Ranking:  " + filme.get("rank"));
            System.out.println("\u001b[1mTítulo: \u001b[0m\u001b[34m " + filme.get("title"));
            System.out.println("\u001b[0mLink:  " + filme.get("image"));
            System.out.println("Classificação:  " + filme.get("imDbRating"));
            
            double classificacao = Double.parseDouble(filme.get("imDbRating"));
            int numeroEstrelinhas = (int)classificacao;
            for (int j = 1; j <= numeroEstrelinhas; j++) {
                System.out.print("⭐");
            
            }
            System.out.println();
        }
        System.out.println();**/
    }
}
