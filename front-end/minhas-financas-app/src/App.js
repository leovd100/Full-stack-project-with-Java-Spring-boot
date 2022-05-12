import React from "react";
import 'bootswatch/dist/flatly/bootstrap.css'
import Login from "./views/login";
import CadastroUsuario from "./views/cadastrUsuario";
import './custom.css'
class App extends React.Component {

  render(){
    return(
      <div className="container">
        <div>
          <CadastroUsuario/>

        </div>
      </div>
    )   
  }
}

export default App;
