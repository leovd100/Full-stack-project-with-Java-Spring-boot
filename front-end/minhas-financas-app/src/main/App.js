import React from "react";
import 'bootswatch/dist/flatly/bootstrap.css'

import '../custom.css'
import Rotas from "./rotas";
class App extends React.Component {

  render(){
    return(
      <div className="container">
        <div>
          <Rotas/>

        </div>
      </div>
    )   
  }
}

export default App;
