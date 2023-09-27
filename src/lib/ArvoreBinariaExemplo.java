/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import java.util.*;

/**
 *
 * @author FeLiXp90, JoaoFerrareis02, etc, etc .....
 */
public class ArvoreBinariaExemplo<T> implements IArvoreBinaria<T> {

    protected NoExemplo<T> raiz = null;
    protected Comparator<T> comparador;

    protected NoExemplo<T> atual = null;
    private ArrayList<NoExemplo<T>> pilhaNavegacao = null;
    private boolean primeiraChamada = true;

    public ArvoreBinariaExemplo(Comparator<T> comp) {
        comparador = comp;
    }


    @Override
    public void adicionar(T novoValor) {
        NoExemplo<T> novoNo = new NoExemplo<>(novoValor);

        if (raiz == null) {
            pilhaNavegacao = new ArrayList<>();
            raiz = novoNo;
            return;
        }

        atual = raiz;

        while (true) {
            int comparacao = comparador.compare(novoValor, atual.getValor());


            if (comparacao < 0) {
                if (atual.getFilhoEsquerda() == null) {
                    atual.setFilhoEsquerda(novoNo);
                    return; // Encerra o método quando o novo valor foi adicionado.
                }
                atual = atual.getFilhoEsquerda();
            } else if (comparacao > 0) {
                if (atual.getFilhoDireita() == null) {
                    atual.setFilhoDireita(novoNo);
                    return; // Encerra o método quando o novo valor foi adicionado.
                }
                atual = atual.getFilhoDireita();
            } else {
                // Se a diferença do comparador for 0, então já existe, se valor já existe na árvore, não faz nada. Ou podemos fazer com que todos nós duplicados seja adicionado à direita. *Cada elemento possui um ramo esquerda (menores) e um ramo direito (maiores ou iguais)*
                return;
            }
        }
    }


    @Override
    public T pesquisar(T valor) {

        if (raiz == null) { // A árvore está vazia, não há nada.
            return null;
        }

        atual = raiz;

        while (atual != null) {
            int comparacao = comparador.compare(valor, atual.getValor());

            if (comparacao == 0) {
                return atual.getValor(); // Valor encontrado.
            } else if (comparacao < 0) {
                // O valor pode estar na subárvore esquerda.
                atual = atual.getFilhoEsquerda();
            } else {
                // O valor pode estar na subárvore direita.
                atual = atual.getFilhoDireita();
            }
        }

        return null; // Valor não encontrado na árvore.
    }

    @Override
    public T remover(T valor) {

        if (raiz == null) {
            return null;
        }

        NoExemplo<T> pai = null;
        atual = raiz;

        while (atual != null) {
            int comparacao = comparador.compare(valor, atual.getValor());

            /*
             * Seguinte, aqui é o mesmo esquema que pesquisar, a arvore vai começar a ser
             * varrida até encontrar o valor. Uma vez encontrado a var atual vai receber
             * o valor e a var pai vai referenciar o nó pai de atual. Então quando,
             * comparacao == 0 ele termina o loop de busca já que atual vai ser valor.
             */
            if (comparacao == 0) {
                break;
            } else if (comparacao < 0) {
                pai = atual;
                atual = atual.getFilhoEsquerda();
            } else {
                pai = atual;
                atual = atual.getFilhoDireita();
            }
        }

        if (atual == null) {
            return null;
        }

        // Fim da pesquisa. Agora temos que remover o nó, mas caso ele tenha filhos?
        // Caso 1: Se o nó atual for uma folha (não tem filhos), simplesmente remova-o.
        if (atual.getFilhoEsquerda() == null && atual.getFilhoDireita() == null) {
            if (pai == null) { //Se pai é nulo, então atual é raiz.
                raiz = null;
            } else if (atual == pai.getFilhoEsquerda()) { //Se não for, verifica se o nó atual é o filho esquerda ou direita do pai (talvez dê para melhorar já dizendo qual lado do pai é atual. PENSEM!!)
                pai.setFilhoEsquerda(null);
            } else if (atual == pai.getFilhoDireita()) {
                pai.setFilhoDireita(null);
            }
            return atual.getValor();
        }

        // Caso 2: Se o nó atual tem um filho (esquerda ou direita).
        // Se for filho a esquerda:
        if (atual.getFilhoEsquerda() != null && atual.getFilhoDireita() == null) {
            if (pai == null) { //Se pai é nulo, então atual é raiz e tem filho a esquerda.
                raiz = atual.getFilhoEsquerda();
            } else if (atual == pai.getFilhoEsquerda()) { //Se não for, verifica se o nó atual é o filho esquerda ou direita do pai.
                pai.setFilhoEsquerda(atual.getFilhoEsquerda());
            } else {
                pai.setFilhoDireita(atual.getFilhoEsquerda());
            }
            return atual.getValor();
        }

        // Se for filho a direita:
        if (atual.getFilhoDireita() != null && atual.getFilhoEsquerda() == null) {
            if (pai == null) {
                raiz = atual.getFilhoDireita();
            } else if (atual == pai.getFilhoEsquerda()) {
                pai.setFilhoEsquerda(atual.getFilhoDireita());
            } else {
                pai.setFilhoDireita(atual.getFilhoDireita());
            }
            return atual.getValor();
        }

        /* Caso 3: O nó atual tem dois filhos.
         * Vamos à lógica:
         * Para remover um nó com dois filhos, precisamos encontrar um substituto.
         * Seja escolhendo o lado da direita ou da esquerda.
         * Vamos à subárvore à esquerda do nó atual para encontrar o substituto.
         * O substituto será o valor mais à direita nessa subárvore, pois é o próximo
         * valor mais próximo e maior que pode substituir o nó atual.
         * Salvamos o pai do substituto, pois o substituto pode ter um filho à esquerda.
         * Caso tenha um filho à esquerda, o pai do substituto precisa referenciar
         * esse filho como seu filho à direita.
         * Após a substituição, atualizamos a estrutura da árvore conforme necessário
         * e retornamos o valor do substituto.
         */
        if (atual.getFilhoEsquerda() != null && atual.getFilhoDireita() != null) {

            NoExemplo<T> paiSubstituto = atual;
            NoExemplo<T> substituto = atual.getFilhoEsquerda();

            while (substituto.getFilhoDireita() != null) {
                paiSubstituto = substituto;
                substituto = substituto.getFilhoDireita();
            }

            atual.setValor(substituto.getValor()); // Agora substituto é o nó que deve substituir o nó atual.

            if (paiSubstituto != atual) { // Verificar se o substituto tem filho à esquerda.
                paiSubstituto.setFilhoDireita(substituto.getFilhoEsquerda());
            } else {
                atual.setFilhoEsquerda(substituto.getFilhoEsquerda()); // Caso especial: o substituto é filho esquerda do nó atual.
            }
            return substituto.getValor();
        }
        return null;
    }


    @Override
    public int altura() {
        if (raiz == null) {
            return -1;
        }

        int altura = -1;
        reiniciarNavegacao(); // Limpa a pilha de navegação.
        pilhaNavegacao.add(raiz); // Adiciona a raiz à pilha.

        while (!pilhaNavegacao.isEmpty()) {
            int tamanhoNivel = pilhaNavegacao.size();

            for (int i = 0; i < tamanhoNivel; i++) {
                NoExemplo<T> no = pilhaNavegacao.remove(0);

                if (no.getFilhoEsquerda() != null) {
                    pilhaNavegacao.add(no.getFilhoEsquerda());
                }

                if (no.getFilhoDireita() != null) {
                    pilhaNavegacao.add(no.getFilhoDireita());
                }
            }

            altura++;
        }

        return altura;
    }



    @Override
    public int quantidadeNos() {
        return contarNos(raiz);
    }

    private int contarNos(NoExemplo<T> no) {
        if (no == null) {
            return 0; // Caso base: nó nulo.
        }

        int nosNaEsquerda = contarNos(no.getFilhoEsquerda());
        int nosNaDireita = contarNos(no.getFilhoDireita());

        return 1 + nosNaEsquerda + nosNaDireita;
    }

    @Override
    public String caminharEmNivel() {
        StringBuilder resultado = new StringBuilder("[");
        if (raiz == null) {
            resultado.append("Vazio]");
        } else {
            ArrayList<NoExemplo<T>> nivelAtual = new ArrayList<>();
            nivelAtual.add(raiz);

            while (!nivelAtual.isEmpty()) {
                ArrayList<NoExemplo<T>> proximoNivel = new ArrayList<>();

                for (NoExemplo<T> no : nivelAtual) {
                    resultado.append(no.getValor().toString());

                    if (no.getFilhoEsquerda() != null) {
                        proximoNivel.add(no.getFilhoEsquerda());
                    }

                    if (no.getFilhoDireita() != null) {
                        proximoNivel.add(no.getFilhoDireita());
                    }

                    if (nivelAtual.indexOf(no) != nivelAtual.size() - 1) {
                        resultado.append("\n");
                    }
                }

                nivelAtual = proximoNivel;
            }
        }

        resultado.append("]");

        return resultado.toString();
    }



    @Override
    public String caminharEmOrdem() {
        StringBuilder resultado = new StringBuilder("[");
        if (raiz == null) {
            resultado.append("Vazio]");
        } else {
            reiniciarNavegacao(); // Limpa a pilha de navegação.
            atual = raiz;

            while (atual != null || !pilhaNavegacao.isEmpty()) {
                while (atual != null) {
                    pilhaNavegacao.add(atual);
                    atual = atual.getFilhoEsquerda();
                }

                int tamanho = pilhaNavegacao.size();
                atual = pilhaNavegacao.remove(tamanho - 1); // Remove o último elemento da pilha (pop).

                resultado.append(atual.getValor().toString()).append("\n");
                atual = atual.getFilhoDireita();
            }
        }

        resultado.append("]");

        return resultado.toString();
    }

    @Override
    public T obterProximo() {
        if (raiz == null){
            return null;
        }

        if (primeiraChamada) {
            primeiraChamada = false;
            atual = raiz;

            while (atual != null) {
                pilhaNavegacao.add(atual);
                atual = atual.getFilhoEsquerda();
            }
        } else {

            if (atual.getFilhoDireita() != null) {
                atual = atual.getFilhoDireita();

                while (atual != null) {
                    pilhaNavegacao.add(atual);
                    atual = atual.getFilhoEsquerda();
                }
            } else {

                NoExemplo<T> pai = pilhaNavegacao.remove(pilhaNavegacao.size() - 1);

                while (!pilhaNavegacao.isEmpty() && atual == pai.getFilhoDireita()) {
                    atual = pai;
                    pai = pilhaNavegacao.remove(pilhaNavegacao.size() - 1);
                }

                atual = pai;
            }
        }

        // Retorna o valor do nó atual.
        return atual != null ? atual.getValor() : null;
    }


    @Override
    public void reiniciarNavegacao() {
        if (pilhaNavegacao != null) {
            primeiraChamada = true;
            pilhaNavegacao.clear();
        }
    }

}
