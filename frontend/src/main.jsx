import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import App from './App.jsx'; 
import Home from './pages/Home.jsx';
import Browse from './pages/Browse.jsx';
import AccountPage from './pages/Account.jsx';
import Me from './pages/Me.jsx';
import ErrorPage from './components/ErrorPage.jsx';
import UserAuthenticationPage from "./pages/Authentication.jsx";


const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    errorElement: <ErrorPage/>,
    children: [
      {
        index: true, 
        element: <Home />,
      },
      {
        path: 'browse/city/:city/date/:date',
        element: <Browse />,
      },
      {
        path: 'login',
        element: <Login />
      },
      {
        path: 'account',
        element: <AccountPage />
      },
      {
        path: 'me',
        element: <Me />
      }
    ]
  }
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
);