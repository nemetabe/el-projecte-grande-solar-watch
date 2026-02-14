import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import App from './App.jsx'; 
import Home from './pages/Home.jsx';
import Browse from './pages/Browse.jsx';
import AccountPage from './pages/AccountPage.jsx';
import MePage from './pages/MePage.jsx';
import ErrorPage from './components/ErrorPage.jsx';
import UserAuthenticationPage from "./pages/UserAuthenticationPage.jsx";


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
        path: 'browse',
        element: <Browse />,
      },
      {
        path: 'user',
        element: <UserAuthenticationPage />,
      },
      {
        path: 'account',
        element: <AccountPage />
      },
      {
        path: 'me',
        element: <MePage />
      },
    ]
  }
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
);