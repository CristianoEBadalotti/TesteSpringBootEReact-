import axios from 'axios'

const INSTRUCTOR = 'cris'
const COMMENT_API_URL = 'http://localhost:8080'
const INSTRUCTOR_API_URL = `${COMMENT_API_URL}/${INSTRUCTOR}`

class CommentDataService {

    retrieveAllComments() {
        return axios.get(`${INSTRUCTOR_API_URL}/comments`);
    }

    retrieveComment(id) {
        return axios.get(`${INSTRUCTOR_API_URL}/comments/${id}`);
    }

    deleteComment(id) {
        return axios.delete(`${INSTRUCTOR_API_URL}/comments/${id}`);
    }

    updateComment(id, comment) {
        return axios.put(`${INSTRUCTOR_API_URL}/comments/${id}`, comment);
    }

    createComment(comment) {
        return axios.post(`${INSTRUCTOR_API_URL}/comments/`, comment);
    }
}

export default new CommentDataService()