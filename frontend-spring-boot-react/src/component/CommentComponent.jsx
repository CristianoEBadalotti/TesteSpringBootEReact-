import React, { Component } from 'react'
import CommentDataService from '../service/CommentDataService';
import { Formik, Form, Field, ErrorMessage } from 'formik';

class CommentsComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            comments: [],
            message: null,
            id: -1,
            username: '',
            description: '',
            upvotes: 0
        }

        this.deleteCommentClicked = this.deleteCommentClicked.bind(this)
        this.refreshComments = this.refreshComments.bind(this)
        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this)
        this.minusClicked = this.minusClicked.bind(this)
        this.plusClicked = this.plusClicked.bind(this)
    }

    componentDidMount() {
        this.refreshComments();
    }

    refreshComments() {
        CommentDataService.retrieveAllComments()//HARDCODED
            .then(
                response => {
                    this.setState({ comments: response.data })
                }
            )
    }

    deleteCommentClicked(id) {
        CommentDataService.deleteComment(id)
            .then(
                response => {
                    this.setState({ message: `Texto apagado com sucesso!` })
                    this.refreshComments()
                }
            )

    }

    minusClicked(comment) {
        comment.upvotes = comment.upvotes - 1
        CommentDataService.updateComment(comment.id, comment)
            .then(() => this.props.history.push('/comments'))
    }

    plusClicked(comment) {
        comment.upvotes = comment.upvotes + 1
        CommentDataService.updateComment(comment.id, comment)
            .then(() => this.props.history.push('/comments'))
    }

    onSubmit(values) {
        let comment = {
            id: this.state.id,
            username: values.username,
            description: values.description,
            upvotes: this.state.upvotes
        }

        values.username = ''
        values.description = ''

        CommentDataService.createComment(comment)
            .then(
                response => {
                    this.setState({ message: `Texto publicado com sucesso!` })
                    this.refreshComments()
                }
                )
    }

    validate(values) {
        let errors = {}
        if (!values.description) {
            errors.description = 'Enter a Description'
        } else if (values.description.length < 5) {
            errors.description = 'Enter atleast 5 Characters in Description'
        }

        return errors

    }

    render() {
        console.log('render')

        let { description, username, upvotes, id } = this.state

        return (
            <div className="container">
                {this.state.message && <div class="alert alert-success">{this.state.message}</div>}
                <Formik
                    initialValues={{ id, description, username, upvotes }}
                    onSubmit={this.onSubmit}
                >
                    {(props) => (
                        <Form>
                            <ErrorMessage name="description" component="div"
                                className="alert alert-warning" />
                            <fieldset className="form-group">
                                <label>Usuário</label>
                                <Field className="form-control" type="text" name="username" />
                            </fieldset>
                            <fieldset className="form-group">
                                <label>Texto</label>
                                <Field className="form-control" type="text" name="description" />
                            </fieldset>
                            <button className="btn btn-success" type="submit">Postar</button>
                        </Form>
                        )
                    }
                </Formik>

                <h3>Publicações:</h3>
               
                
                    <table className="table">
                        <tbody>
                            {
                            this.state.comments.map(
                                    comment =>
                                    <tr key={comment.id}>
                                        <td>{comment.username}</td>
                                        <td>{comment.description}</td>
                                        <td><button className="btn btn-success" onClick={() => this.minusClicked(comment)}> - </button> {comment.upvotes} <button className="btn btn-success" onClick={() => this.plusClicked(comment)}> + </button></td>
                                        <td><button className="btn btn-warning" onClick={() => this.deleteCommentClicked(comment.id)}>Apagar</button></td>
                                     </tr>
                                )
                            }
                        </tbody>
                    </table>
                
            </div>
        )
    }
}

export default CommentsComponent