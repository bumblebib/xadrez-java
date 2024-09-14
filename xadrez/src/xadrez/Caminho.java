package xadrez;

import java.util.ArrayList;
import java.util.List;

public class Caminho {
    private List<Casa> casas;
    private Casa casaInicial;
    private Casa casaFinal; //casa incial e final separadas

    public Caminho(Casa inicio, Casa fim) {
        
        if(inicio == null || fim == null) {
            throw new IllegalArgumentException("O inicio/fim do caminho nao pode ser nulo");
        }
        
        casaInicial = inicio;
        casaFinal = fim;
        casas = new ArrayList<>();
    }

    public void adicionarCasa(Casa casa) {
        
        if(casa == null) throw new IllegalArgumentException("Essa casa nao existe");
        
        casas.add(casa);
    } //colocando casa no caminho
    
    public Casa getCasaInicial() {
        return casaInicial;
    } //o construtor nao vai deixar casas nulas entrarem aqui ent acho 
    
    public Casa getCasaFinal() { //que nao precisa de verificacao aqui
        return casaFinal;
    }
    
    public boolean estaLivre() {
        if(casas.isEmpty()) return true; //se nao existe caminho entre, liberado automaticamente

        for (Casa c : casas) {
            if (c.estaOcupada()) return false; //tbm nao vao entrar casas nulas no caminho
        } //se algum das casas no caminho estiver ocupada
        
        return true;
    } 

    
}