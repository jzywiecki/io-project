import NoPermitionPage from "../pages/NoPermitionPage";

const PrivateRoute=({children})=>(localStorage.getItem("role")==="TEACHER"?<>{children}</>:<NoPermitionPage/>)

export default PrivateRoute;