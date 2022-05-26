import React from "react";
import { withRouter } from 'react-router-dom'
import { Dialog } from 'primereact/dialog';
import Card from "../../components/card";
import FormGroup from "../../components/formgroup";
import SelectMenu from "../../components/selectMenu";
import LancamentosTable from "./lancamentosTable";
import LancamentoService from "../../app/service/lancamentoService";
import LocalStorageService from "../../app/service/localStorageService";
import * as message from '../../components/toastr'

import '../../button-demo.css'
class ConsultaLancamentos extends React.Component {
    state = {
        ano: '',
        mes: '',
        tipo: '',
        descricao: '',
        lancamentos: [],
        lancamentoDeletar: {},
        showConfirmDialog : false

    }
    constructor() {
        super()
        this.service = new LancamentoService()
    }

    

    editar = (id) => {
        this.changePage(`/cadastro-lancamentos/${id}`,0)
 
    }

    abrirConfirmacao = (lancamentoValue) =>{
      
        this.setState({showConfirmDialog: true})
        this.setState({lancamentoDeletar: lancamentoValue})
        
    }

    cancelarDelecao(){
        this.setState({showConfirmDialog: false, lancamentoDeletar: {}})
    }

    deletar = () => {
        
        this.service.deletar(this.state.lancamentoDeletar.id)

            .then(response => {
                const lancamentos = this.state.lancamentos
                const index = lancamentos.indexOf(this.state.lancamentoDeletar)
                lancamentos.splice(index, 1)
                this.setState({lancamentos:lancamentos, showConfirmDialog: false })
                message.mensagemSucesso('Lançamento deletado com sucesso!')

            }).catch(err => {
                message.mensagemErro('Ocorreu um erro ao tentar deletar o lançamento.')
            })
    }

    changePage = (away, time) => {
        this.props.history.push(away)
        setTimeout(() => {
            window.location.reload();
        },time)
    }

    preparaFormularioCadastro = (time) => {
        this.changePage('/cadastro-lancamentos',time)
    }

    

    buscar = () => {
        if (!this.state.ano) {
            message.mensagemErro('O preenchimento do campo ano é obrigatório.')
            return false;
        }
        const usuarioLogado = LocalStorageService.obterItem('_usuarioLogado')
        const lancamentoFiltro = {
            ano: this.state.ano,
            mes: this.state.mes,
            tipo: this.state.tipo,
            descricao: this.state.descricao,
            usuario: usuarioLogado.id
        }

        this.service.consultar(lancamentoFiltro)
            .then(response => {
                const lista = response.data
                if(lista.length < 1){
                    message.mensagemAlerta("Nenhum resultado encontrado")
                }
                this.setState({ lancamentos: response.data })
            }).catch(err => {
                console.log(err)
            })
    }

    alterarStatus = (lancamento, status) => {
        this.service.alterarStatus(lancamento.id,status)
            .then(response => {
                const lancamentos = this.state.lancamentos
                const index = lancamentos.indexOf(lancamento)
                if(index !== -1){
                    lancamento['status'] = status
                    lancamentos[index] = lancamento
                    this.setState({lancamentos})
                }
                message.mensagemSucesso("Status atualizado com sucesso")
            })             
    }

    render() {
        const meses = this.service.opterListaMeses();
        const tipos = this.service.obterListTipos();



        const confirmDialogFooter = (
       
                <div>
                    <button onClick={() => this.deletar()} type="button" className="btn btn-success">Confirma</button>
                    <button type="button" className="btn btn-danger" onClick={() => this.cancelarDelecao}>Cancelar</button>
                </div>
        )
        
       
    

        return (
            <Card title="Consulta Lancamentos">
                <div className="row">
                    <div className="col-md-6">
                        <div className="bs-component">
                            <FormGroup label="Ano" htmlFor="inputAno">
                                <input
                                    type="text"
                                    className="form-control"
                                    id="inputAno"
                                    value={this.state.ano}
                                    onChange={e => this.setState({ ano: e.target.value })}
                                    placeholder="Digite o Ano"
                                ></input>
                            </FormGroup>

                            <FormGroup label="Mês" htmlFor="inputMes">
                                <SelectMenu id="inputMes"
                                    className="form-control"
                                    value={this.state.mes}
                                    onChange={e => this.setState({ mes: this.target.value })}
                                    lista={meses} />
                            </FormGroup>


                            <FormGroup label="Descrição" htmlFor="inputDesc">
                                <input
                                    type="text"
                                    className="form-control"
                                    id="inputDescricao"
                                    value={this.state.descricao}
                                    onChange={e => this.setState({ descricao: e.target.value })}
                                    placeholder="Digite a descrição"
                                ></input>
                            </FormGroup>


                            <FormGroup label="Tipo Lancamento" htmlFor="inputTipo">
                                <SelectMenu id="inputTipo"
                                    value={this.state.tipo}
                                    onChange={e => this.setState({ tipo: this.target.value })}
                                    className="form-control" lista={tipos} />
                            </FormGroup>
                            <br />
                            <br />
                            <button onClick={this.buscar} type="button" className="btn btn-success">Buscar</button>
                            <button onClick={e => this.preparaFormularioCadastro()} type="button" className="btn btn-danger">Cadastrar</button>
                        </div>
                    </div>
                </div>
                <br></br>
                <div className="row">
                    <div className="col-md-12">
                        <div className="bs-component">
                            <LancamentosTable lancamentos={this.state.lancamentos}
                                deletAction={this.abrirConfirmacao}
                                editAction={this.editar}
                                alterarStatus={this.alterarStatus}></LancamentosTable>
                                
                        </div>
                    </div>
                </div>
                <div>
                    <Dialog header="Confirmação" 
                            visible={this.state.showConfirmDialog} 
                            style={{ width: '50vw', border: '2px solid black', background: 'white', borderRadius: '5px' , padding: '5px' }} 
                            modal={true}
                            footer={confirmDialogFooter}
                            onHide={() => this.setState({showConfirmDialog: false})}>
                        <p>Confirma a exclusão deste lancamento?</p>
                    </Dialog>


                </div>

            </Card>
        )
    }
}

export default withRouter(ConsultaLancamentos);