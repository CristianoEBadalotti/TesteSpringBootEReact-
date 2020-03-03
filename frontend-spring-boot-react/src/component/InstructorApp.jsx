import React, { Component } from 'react';
import CommentComponent from './CommentComponent';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'

class InstructorApp extends Component {
    render() {
        return (
            <Router>
                <>
                    <h1>Teste Spring Boot + React</h1>
                    <Switch>
                        <Route path="/" exact component={CommentComponent} />
                        <Route path="/comments" exact component={CommentComponent} />
                    </Switch>
                </>
            </Router>
        )
    }
}

export default InstructorApp