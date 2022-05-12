import React from "react";
import 'bootswatch/dist/flatly/bootstrap.css'
import NavBar from "../components/navBar";
import '../custom.css'
import Rotas from "./rotas";
class App extends React.Component {

  render() {
    return (
      <>
        <NavBar />
        <div className="container">
          <Rotas />
        </div>
      </>
    )
  }
}

export default App;
